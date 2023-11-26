package com.growbiz.backend.Payment.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Email.service.ISendEmailService;
import com.growbiz.backend.Enums.PaymentStatus;
import com.growbiz.backend.Payment.helper.PaymentServiceHelper;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.RequestResponse.Payment.PaymentRequest;
import com.growbiz.backend.RequestResponse.Payment.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    IPaymentRepository paymentRepository;
    @Autowired
    ISendEmailService sendEmailService;
    @Autowired
    PaymentServiceHelper helper;

    @Value("${keys.stripeAPIKey}")
    private String stripeAPIKey;
    @Value("${keys.stripeWebhookSecret}")
    private String stripeWebhookSecret;
    private static final String EMAIL_SUBJECT = "Payment Confirmation: Successful Transaction for ";

    @Override
    public Payment addPayment(PaymentRequest paymentRequest, long amount) {
        return paymentRepository.save(helper.createPayment(paymentRequest, amount));
    }

    @Override
    public Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public List<Payment> findAllPayments() {
        return StreamSupport.stream(paymentRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public List<Payment> findAllPaymentsByUserEmail(String userEmail) {
        return paymentRepository.findByUserEmail(userEmail);
    }

    @Override
    public ResponseEntity<String> handleWebhook(String requestBody, String sigHeader) {
        Stripe.apiKey = stripeAPIKey;
        Event event = null;
        try {
            event = Webhook.constructEvent(requestBody, sigHeader, stripeWebhookSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.internalServerError().build();
        }
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        }
        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        if (Objects.isNull(paymentIntent)) {
            return ResponseEntity.internalServerError().build();
        }
        switch (PaymentStatus.getStatusFromValue(event.getType())) {
            case SUCCESS -> {
                Long paymentId = Long.parseLong(paymentIntent.getMetadata().get("payment_id"));
                Payment payment = findPaymentById(paymentId);
                Booking booking = helper.saveToBooking(payment, paymentIntent.getAmount());
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment.setBookingId(booking.getId());
                helper.updatePayment(payment);
                sendEmailPaymentSuccess(booking);
            }
            case CREATED -> {
                Long paymentId = Long.parseLong(paymentIntent.getMetadata().get("payment_id"));
                Payment payment = findPaymentById(paymentId);
                payment.setPaymentStatus(PaymentStatus.CREATED);
                helper.updatePayment(payment);
            }
            default -> System.err.println("Unhandled event type: " + event.getType());
        }
        return ResponseEntity.ok().build();
    }

    private void sendEmailPaymentSuccess(Booking booking) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("Your payment of ")
                .append(booking.getAmount())
                .append("for the service has been processed, securing your booking for ")
                .append(booking.getDate())
                .append(" at ")
                .append(booking.getStartTime())
                .append("\n")
                .append("We're excited to have you with us and ensure a fantastic experience.");
        sendEmailService.sendEmail(booking.getUser().getEmail(), EMAIL_SUBJECT + booking.getService().getServiceName(), emailBody.toString());
    }

    @Override
    public ResponseEntity<PaymentResponse> createPaymentIntent(PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeAPIKey;
        try {
            long totalAmount = helper.calculatePaymentAmount(paymentRequest.getServiceId());
            Payment payment = addPayment(paymentRequest, totalAmount);
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .putMetadata("payment_id", payment.getPaymentId().toString())
                            .setAmount(totalAmount)
                            .setCurrency("cad")
                            .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            payment.setClientSecret(paymentIntent.getClientSecret());
            helper.updatePayment(payment);
            PaymentResponse paymentResponse = PaymentResponse.builder().clientSecret(paymentIntent.getClientSecret()).paymentId(payment.getPaymentId()).build();
            return ResponseEntity.ok().body(paymentResponse);
        } catch (StripeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public List<Payment> findByServiceId(Long serviceId) {
        return paymentRepository.findByServiceId(serviceId);
    }
}
