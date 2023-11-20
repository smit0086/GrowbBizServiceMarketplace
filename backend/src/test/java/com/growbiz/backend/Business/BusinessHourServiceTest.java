package com.growbiz.backend.Business;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import com.growbiz.backend.BusinessHour.repository.IBusinessHourRepository;
import com.growbiz.backend.BusinessHour.service.BusinessHourService;
import com.growbiz.backend.RequestResponse.BusinessHour.BusinessHourRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
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
                .businessId(1L)
                .businessHours(mockedMapOfBusinessHour)
                .build();
        doReturn(null).when(businessHourRepositoryMock).save(any());
        when(businessHourRepositoryMock.findById(1L)).thenReturn(Optional.of(mockedBusinessHour));
    }

    @Test
    public void testSaveBusinessHours() {
        businessHourServiceMock.saveBusinessHours(mockedBusinessHourRequest);
    }

    @Test
    public void testGetBusinessHour() {
        Assertions.assertEquals(mockedBusinessHour, businessHourServiceMock.getBusinessHour(1L));
    }

    @Test
    public void testInit() {
        businessHourServiceMock.init(1L);
    }
}
