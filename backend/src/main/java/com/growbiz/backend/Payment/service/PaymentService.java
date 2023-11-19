package com.growbiz.backend.Payment.service;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.models.BookingStatus;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.model.PaymentRequest;
import com.growbiz.backend.Payment.model.PaymentResponse;
import com.growbiz.backend.Payment.model.PaymentStatus;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.Role;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    IPaymentRepository paymentRepository;
    @Autowired
    IServicesService servicesService;
    @Autowired
    IBookingService bookingService;
    @Autowired
    IUserService userService;

    @Value("${keys.stripeAPIKey}")
    private String stripeAPIKey;
    @Value("${keys.stripeWebhookSecret}")
    private String stripeWebhookSecret;

    @Override
    public Payment addPayment(PaymentRequest paymentRequest, long amount) {
        return paymentRepository.save(createPayment(paymentRequest, amount));
    }

    @Override
    public Payment findPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    @Override
    public List<Payment> findAllPayments() {
        return StreamSupport.stream(paymentRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public  List<Payment> findAllPaymentsByUserEmail(String userEmail) {
        return paymentRepository.findByUserEmail(userEmail);
    }

    @Override
    public List<Payment> findByPaymentStatus(PaymentStatus paymentStatus) {
        return paymentRepository.findByPaymentStatusEquals(paymentStatus);
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
                Payment payment = fetchPayment(paymentIntent);
                Booking booking = saveToBooking(payment, paymentIntent.getAmount());
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment.setBookingId(booking.getId());
                updatePayment(payment);
            }
            case CREATED -> {
                Payment payment = fetchPayment(paymentIntent);
                payment.setPaymentStatus(PaymentStatus.CREATED);
                updatePayment(payment);
            }
            default -> System.out.println("Unhandled event type: " + event.getType());
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<PaymentResponse> createPaymentIntent(PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeAPIKey;
        try {
            long totalAmount = calculatePaymentAmount(paymentRequest.getServiceId());
            Payment payment = addPayment(paymentRequest, totalAmount);
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .putMetadata("payment_id", payment.getPaymentId().toString())
                            .setAmount(totalAmount)
                            .setCurrency("cad")
                            .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            payment.setClientSecret(paymentIntent.getClientSecret());
            updatePayment(payment);
            return ResponseEntity.ok().body(PaymentResponse.builder().clientSecret(paymentIntent.getClientSecret()).paymentId(payment.getPaymentId()).build());
        } catch (StripeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public List<Payment> findByServiceId(Long serviceId) {
        return paymentRepository.findByServiceId(serviceId);
    }

    private Payment createPayment(PaymentRequest paymentRequest, long amount) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Payment.builder()
                .serviceId(paymentRequest.getServiceId())
                .date(paymentRequest.getDate())
                .startTime(paymentRequest.getStartTime())
                .endTime(paymentRequest.getEndTime())
                .note(paymentRequest.getNote())
                .userEmail(user.getUsername())
                .amount((double) amount / 100)
                .build();
    }

    private void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    private long calculatePaymentAmount(Long serviceId) {
        Services service = servicesService.getServiceById(serviceId);
        double servicePrice = service.getPrice();
        double taxApplied = Double.parseDouble(service.getSubCategory().getCategory().getTax());
        return (long) ((servicePrice + ((taxApplied / 100) * servicePrice)) * 100);
    }

    private Payment fetchPayment(PaymentIntent paymentIntent) {
        Long paymentId = Long.parseLong(paymentIntent.getMetadata().get("payment_id"));
        return findPaymentById(paymentId);
    }

    private Booking saveToBooking(Payment payment, Long amount) {
        User user = userService.getUserByEmailAndRole(payment.getUserEmail(), Role.CUSTOMER.name());
        Services service = servicesService.getServiceById(payment.getServiceId());
        Booking booking = Booking.builder()
                .amount(amount)
                .date(payment.getDate())
                .endTime(payment.getEndTime())
                .startTime(payment.getStartTime())
                .note(payment.getNote())
                .user(user)
                .status(BookingStatus.UPCOMING)
                .service(service)
                .build();
        return bookingService.save(booking);
    }
}
