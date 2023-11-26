package com.growbiz.backend.BusinessHour;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.repository.IBusinessHourRepository;
import com.growbiz.backend.BusinessHour.service.BusinessHourService;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourRequest;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessHourServiceTest {

    @InjectMocks
    private BusinessHourService businessHourServiceMock;
    @Mock
    private IBusinessHourRepository businessHourRepositoryMock;
    @Mock
    private BusinessHour mockedBusinessHour;
    BusinessHourRequest mockedBusinessHourRequest;

    @BeforeEach
    void init() {
        Map<DayOfWeek, String[]> mockedMapOfBusinessHour = new HashMap<>();
        String[] mockedTimeSlots = {"09:30", "17:00"};
        mockedMapOfBusinessHour.put(DayOfWeek.MONDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.TUESDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.WEDNESDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.THURSDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.FRIDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.SATURDAY, mockedTimeSlots);
        mockedMapOfBusinessHour.put(DayOfWeek.SUNDAY, mockedTimeSlots);
        mockedBusinessHourRequest = BusinessHourRequest.builder()
                .businessId(TestConstants.TEST_ID_1)
                .businessHours(mockedMapOfBusinessHour)
                .build();

    }

    @Test
    void testSaveBusinessHours() {
        doReturn(null).when(businessHourRepositoryMock).save(any());
        businessHourServiceMock.saveBusinessHours(mockedBusinessHourRequest);
    }

    @Test
    void testGetBusinessHour() {
        when(businessHourRepositoryMock.findById(TestConstants.TEST_ID_1)).thenReturn(Optional.of(mockedBusinessHour));
        Assertions.assertEquals(mockedBusinessHour, businessHourServiceMock.getBusinessHour(TestConstants.TEST_ID_1));
    }

    @Test
    void testInit() {
        businessHourServiceMock.init(TestConstants.TEST_ID_1);
    }
}
