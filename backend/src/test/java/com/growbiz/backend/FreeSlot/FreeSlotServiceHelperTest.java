package com.growbiz.backend.FreeSlot;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.Enums.PaymentStatus;
import com.growbiz.backend.FreeSlot.helper.FreeSlotServiceHelper;
import com.growbiz.backend.FreeSlot.models.SlotRange;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FreeSlotServiceHelperTest {
    @InjectMocks
    FreeSlotServiceHelper freeSlotServiceHelperMock;
    @Mock
    private IServicesService servicesServiceMock;
    Services mockService;
    BusinessHour mockedBusinessHour;
    List<Payment> mockedPaymentList;

    @BeforeEach
    public void init() {
        Payment mockedPayment1 = Payment.builder()
                .paymentId(1L)
                .amount((double) TestConstants.TEST_AMOUNT)
                .date("2023-11-21")
                .note(TestConstants.TEST_NOTE)
                .userEmail(TestConstants.TEST_EMAIL)
                .paymentStatus(PaymentStatus.SUCCESS)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(11, 0))
                .serviceId(1L)
                .build();
        Payment mockedPayment2 = Payment.builder()
                .paymentId(2L)
                .amount((double) TestConstants.TEST_AMOUNT)
                .date("2023-11-21")
                .note(TestConstants.TEST_NOTE)
                .userEmail(TestConstants.TEST_EMAIL)
                .paymentStatus(PaymentStatus.SUCCESS)
                .startTime(LocalTime.of(11, 0))
                .endTime(LocalTime.of(12, 0))
                .serviceId(1L)
                .build();
        mockedPaymentList = List.of(mockedPayment1, mockedPayment2);
        mockedBusinessHour = BusinessHour.builder()
                .monday_start(TestConstants.TEST_START_LOCAL_TIME)
                .monday_end(TestConstants.TEST_END_LOCAL_TIME)
                .tuesday_start(TestConstants.TEST_START_LOCAL_TIME)
                .tuesday_end(TestConstants.TEST_END_LOCAL_TIME)
                .build();
        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(24.00)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .timeRequired(LocalTime.of(1, 0))
                .build();

    }

    @Test
    void testGetCurrentWeekAllDates() throws ParseException {
        List<Date> expectedListOfDates = new ArrayList<>();
        SimpleDateFormat mockSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-20"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-21"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-22"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-23"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-24"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-25"));
        expectedListOfDates.add(mockSimpleDateFormat.parse("2023-11-26"));
        List<Date> actualValue = freeSlotServiceHelperMock.getCurrentWeekAllDates(new Date());
        Assertions.assertEquals(mockSimpleDateFormat.format(expectedListOfDates.get(0)), mockSimpleDateFormat.format(actualValue.get(0)));
    }

    @Test
    void testGetFreeSlots() throws ParseException {
        SimpleDateFormat mockSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SlotRange slotRange1 = new SlotRange(LocalTime.of(9, 0), LocalTime.of(10, 0));
        SlotRange slotRange2 = new SlotRange(LocalTime.of(12, 0), LocalTime.of(13, 0));
        List<SlotRange> expectedValueTuesday = List.of(slotRange1, slotRange2);
        when(servicesServiceMock.getServiceById(1L)).thenReturn(mockService);
        List<SlotRange> actualValueTue = freeSlotServiceHelperMock.getFreeSlots(mockSimpleDateFormat.parse("2023-11-21"), mockedBusinessHour, 1L, mockedPaymentList);
        Assertions.assertEquals(expectedValueTuesday, actualValueTue);
    }
}
