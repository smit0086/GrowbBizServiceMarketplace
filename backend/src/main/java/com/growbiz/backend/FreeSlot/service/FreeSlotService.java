package com.growbiz.backend.FreeSlot.service;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.Enums.PaymentStatus;
import com.growbiz.backend.FreeSlot.helper.FreeSlotServiceHelper;
import com.growbiz.backend.FreeSlot.models.SlotRange;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.service.IPaymentService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FreeSlotService implements IFreeSlotService {

    @Autowired
    private final FreeSlotServiceHelper helper;

    @Autowired
    private final IBusinessHourService businessHourService;

    @Autowired
    private final IPaymentService paymentService;

    @Autowired
    private final IServicesService servicesService;

    /**
     * The method does the following things:
     * 1. get all the current Week dates.
     * 2. fetches the business hour of the given businessId.
     * 3. fetch all the services of the given businessId.
     * 4. fetch all the paymentList of the services fetched in step3.
     * 5. filter out the payments which are not in the current week and doesn't have status COMPLETED OR CREATED
     * 6. get free slots for all the days in the current week using getFreeSlots method.
     * 7. sort the final list using the date and return it.
     *
     * @param businessId - businessId
     * @param date       - date
     * @param serviceId  - serviceId
     * @return - map of Date and All the free slots
     * @author - an370985@dal.ca
     */
    @Override
    public Map<Date, List<SlotRange>> getFreeSlotsForWeek(Long businessId, Date date, Long serviceId) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<Date, List<SlotRange>> freeSlots = new HashMap<>();
        List<Date> dateListOfCurrentWeek = helper.getCurrentWeekAllDates(date);
        BusinessHour businessHour = businessHourService.getBusinessHour(businessId);
        List<Payment> paymentList = getPaymentList(serviceId, formatter, dateListOfCurrentWeek);
        dateListOfCurrentWeek.forEach(day -> freeSlots.put(day, helper.getFreeSlots(date, businessHour, serviceId, paymentList)));
        return freeSlots.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private List<Payment> getPaymentList(Long serviceId, SimpleDateFormat formatter, List<Date> dateListOfCurrentWeek) {
        List<Services> servicesList = servicesService.getServiceByBusinessId(servicesService.getServiceById(serviceId).getBusiness().getBusinessId());
        List<Payment> paymentList = new ArrayList<>();
        servicesList.forEach(services -> paymentList.addAll(paymentService.findByServiceId(services.getServiceId())));
        paymentList.removeIf(payment -> {
            try {
                return !dateListOfCurrentWeek.contains(formatter.parse(payment.getDate()))
                        && !PaymentStatus.CREATED.equals(payment.getPaymentStatus())
                        && !PaymentStatus.SUCCESS.equals(payment.getPaymentStatus());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        return paymentList;
    }
}
