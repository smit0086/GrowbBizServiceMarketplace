package com.growbiz.backend.Payment.helper;

import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Booking.service.IBookingService;
import com.growbiz.backend.Enums.BookingStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.repository.IPaymentRepository;
import com.growbiz.backend.RequestResponse.Payment.PaymentRequest;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceHelper {

    @Autowired
    IPaymentRepository paymentRepository;
    @Autowired
    IServicesService servicesService;
    @Autowired
    IBookingService bookingService;
    @Autowired
    IUserService userService;
    private static final int HUNDRED = 100;

    public void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public long calculatePaymentAmount(Long serviceId) {
        Services service = servicesService.getServiceById(serviceId);
        double servicePrice = service.getPrice();
        double taxApplied = Double.parseDouble(service.getSubCategory().getCategory().getTax());
        double finalAmount = (servicePrice + ((taxApplied / HUNDRED) * servicePrice)) * HUNDRED;
        return (long) finalAmount;
    }

    public Booking saveToBooking(Payment payment, Long amount) {
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

    public Payment createPayment(PaymentRequest paymentRequest, long amount) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Payment.builder()
                .serviceId(paymentRequest.getServiceId())
                .date(paymentRequest.getDate())
                .startTime(paymentRequest.getStartTime())
                .endTime(paymentRequest.getEndTime())
                .note(paymentRequest.getNote())
                .userEmail(user.getUsername())
                .amount((double) amount / HUNDRED)
                .build();
    }
}
