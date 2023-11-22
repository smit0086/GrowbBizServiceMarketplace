package com.growbiz.backend.FreeSlot;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.Enums.PaymentStatus;
import com.growbiz.backend.FreeSlot.helper.FreeSlotServiceHelper;
import com.growbiz.backend.FreeSlot.models.SlotRange;
import com.growbiz.backend.FreeSlot.service.FreeSlotService;
import com.growbiz.backend.Payment.model.Payment;
import com.growbiz.backend.Payment.service.IPaymentService;
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

import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FreeSlotServiceTest {

    @InjectMocks
    private FreeSlotService freeSlotServiceMock;
    @Mock
    private FreeSlotServiceHelper helperMock;
    @Mock
    private IBusinessHourService businessHourServiceMock;
    @Mock
    private IPaymentService paymentServiceMock;
    @Mock
    private IServicesService servicesServiceMock;
    BusinessHour mockedBusinessHour;
    List<Services> mockedServicesList;
    Date mockedDate;
    List<Payment> mockedPaymentList;
    List<SlotRange> mockedSlotRangeList;
    Services mockService;

    @BeforeEach
    public void init() {
        mockedDate = new Date();
        mockedBusinessHour = BusinessHour.builder()
                .monday_start(TestConstants.TEST_START_LOCAL_TIME)
                .monday_end(TestConstants.TEST_END_LOCAL_TIME)
                .tuesday_start(TestConstants.TEST_START_LOCAL_TIME)
                .tuesday_end(TestConstants.TEST_END_LOCAL_TIME)
                .build();
        Business mockBusiness = Business
                .builder()
                .businessId(TestConstants.TEST_ID_1)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .email(TestConstants.TEST_EMAIL)
                .build();
        mockService = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .build();
        mockedServicesList = List.of(mockService);
        Payment mockedPayment1 = Payment.builder()
                .paymentId(TestConstants.TEST_ID_1)
                .amount((double) TestConstants.TEST_AMOUNT)
                .date("2023-11-21")
                .note(TestConstants.TEST_NOTE)
                .userEmail(TestConstants.TEST_EMAIL)
                .paymentStatus(PaymentStatus.SUCCESS)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(11, 0))
                .serviceId(TestConstants.TEST_ID_1)
                .build();
        Payment mockedPayment2 = Payment.builder()
                .paymentId(TestConstants.TEST_ID_2)
                .amount((double) TestConstants.TEST_AMOUNT)
                .date("2023-11-21")
                .note(TestConstants.TEST_NOTE)
                .userEmail(TestConstants.TEST_EMAIL)
                .paymentStatus(PaymentStatus.SUCCESS)
                .startTime(LocalTime.of(11, 0))
                .endTime(LocalTime.of(12, 0))
                .serviceId(TestConstants.TEST_ID_1)
                .build();
        Payment mockedPayment3 = Payment.builder()
                .paymentId(TestConstants.TEST_ID_3)
                .amount((double) TestConstants.TEST_AMOUNT)
                .date("2023-11-21")
                .note(TestConstants.TEST_NOTE)
                .userEmail(TestConstants.TEST_EMAIL)
                .paymentStatus(PaymentStatus.FAILED)
                .startTime(LocalTime.of(11, 0))
                .endTime(LocalTime.of(12, 0))
                .serviceId(TestConstants.TEST_ID_1)
                .build();
        mockedPaymentList = List.of(mockedPayment1, mockedPayment2, mockedPayment3);
        SlotRange slotRange1 = new SlotRange(LocalTime.of(9, 0), LocalTime.of(10, 0));
        SlotRange slotRange2 = new SlotRange(LocalTime.of(12, 0), LocalTime.of(13, 0));
        mockedSlotRangeList = List.of(slotRange1, slotRange2);
    }

    @Test
    void testGetFreeSlotsForWeek() {
        when(helperMock.getCurrentWeekAllDates(any(Date.class))).thenReturn(List.of(mockedDate));
        when(businessHourServiceMock.getBusinessHour(anyLong())).thenReturn(mockedBusinessHour);
        when(servicesServiceMock.getServiceById(TestConstants.TEST_ID_1)).thenReturn(mockService);
        when(servicesServiceMock.getServiceByBusinessId(anyLong())).thenReturn(mockedServicesList);
        when(paymentServiceMock.findByServiceId(anyLong())).thenReturn(mockedPaymentList);
        when(helperMock.getFreeSlots(any(Date.class), any(BusinessHour.class), anyLong(), any(List.class))).thenReturn(mockedSlotRangeList);
        Map<Date, List<SlotRange>> actualMap = new LinkedHashMap<>();
        actualMap.put(mockedDate, mockedSlotRangeList);
        Assertions.assertEquals(actualMap, freeSlotServiceMock.getFreeSlotsForWeek(TestConstants.TEST_ID_1, mockedDate, TestConstants.TEST_ID_1));
    }
}
