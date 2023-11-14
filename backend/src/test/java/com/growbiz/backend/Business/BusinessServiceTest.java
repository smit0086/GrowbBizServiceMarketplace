package com.growbiz.backend.Business;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessRequest;
import com.growbiz.backend.Business.model.BusinessStatus;
import com.growbiz.backend.Business.repository.IBusinessRepository;
import com.growbiz.backend.Business.service.BusinessService;
import com.growbiz.backend.Business.service.IBusinessHourService;
import com.growbiz.backend.Business.service.IFileStorageService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Exception.exceptions.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.BusinessNotFoundException;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessServiceTest {

    @InjectMocks
    private BusinessService businessServiceMock;
    @Mock
    private IBusinessRepository businessRepositoryMock;
    @Mock
    private IFileStorageService fileStorageServiceMock;
    @Mock
    private ICategoryService categoryServiceMock;
    @Mock
    private IBusinessHourService businessHourServiceMock;
    Business mockedBusiness;
    Category mockedCategory;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockedCategory = Category.builder().categoryID(1L).name("TestCategory").tax(TestConstants.TEST_CATEGORY_TAX).build();
        mockedBusiness = Business.builder().businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .email(TestConstants.TEST_EMAIL)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .status(BusinessStatus.PENDING)
                .category(mockedCategory)
                .build();
        when(businessRepositoryMock.findByStatusEquals(BusinessStatus.PENDING)).thenReturn(List.of(mockedBusiness));
        when(businessRepositoryMock.findAll()).thenReturn(List.of(mockedBusiness));
        when(businessRepositoryMock.findByEmail(TestConstants.TEST_EMAIL)).thenReturn(mockedBusiness);
        when(businessRepositoryMock.findById(1L)).thenReturn(Optional.of(mockedBusiness));
        when(fileStorageServiceMock.uploadFileToStorage(any(MultipartFile.class), Mockito.eq("testEmail@dal.ca")))
                .thenReturn(TestConstants.TEST_BUSINESS_FILE_PATH);
        when(categoryServiceMock.getCategoryByID(1L)).thenReturn(mockedCategory);
        doNothing().when(businessHourServiceMock).init(anyLong());
    }

    @Test
    public void testFetchBusinesses() {
        List<Business> listOfBusinessWhenStatus = businessServiceMock.fetchBusinesses(BusinessStatus.PENDING.name());
        List<Business> listOfBusinessWhenStatusIsNull = businessServiceMock.fetchBusinesses(null);
        Assertions.assertEquals(List.of(mockedBusiness), listOfBusinessWhenStatusIsNull);
        Assertions.assertEquals(List.of(mockedBusiness), listOfBusinessWhenStatus);
    }

    @Test
    public void testFindById() {
        Business actualBusinessWithCorrectId = businessServiceMock.findById(1L);
        Assertions.assertEquals(mockedBusiness, actualBusinessWithCorrectId);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> businessServiceMock.findById(2L));
    }

    @Test
    public void testFindByEmail() {
        Business actualBusinessWithCorrectEmail = businessServiceMock.findByEmail(TestConstants.TEST_EMAIL);
        Assertions.assertEquals(mockedBusiness, actualBusinessWithCorrectEmail);
        Assertions.assertThrows(BusinessNotFoundException.class, () -> businessServiceMock.findByEmail("testEmailIncorrect@dal.ca"));
    }

    @Test
    public void testSaveWithBusinessRequest() {
        when(businessRepositoryMock.findByEmail(TestConstants.TEST_EMAIL)).thenReturn(null);
        when(businessRepositoryMock.save(any(Business.class))).thenReturn(mockedBusiness);
        BusinessRequest mockedBusinessRequest = BusinessRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .role(Role.PARTNER)
                .file(new MockMultipartFile(TestConstants.TEST_FILE_NAME, new byte[]{anyByte()}))
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .categoryId(1L)
                .build();
        Business actualBusiness = businessServiceMock.save(mockedBusinessRequest);
        Assertions.assertEquals(mockedBusiness, actualBusiness);
    }

    @Test
    public void testBusinessAlreadyExistsException() {
        BusinessRequest mockedBusinessRequest = BusinessRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .role(Role.PARTNER)
                .file(new MockMultipartFile(TestConstants.TEST_FILE_NAME, new byte[]{anyByte()}))
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .categoryId(1L)
                .build();
        Assertions.assertThrows(BusinessAlreadyExistsException.class, () -> businessServiceMock.save(mockedBusinessRequest));
    }

    @Test
    public void testSave() {
        businessServiceMock.save(mockedBusiness);
    }

    @Test
    public void testUpdateBusiness() {
        byte[] mockByteArr = new byte[2];
        BusinessRequest mockedBusinessRequest = BusinessRequest.builder()
                .email(TestConstants.TEST_EMAIL)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .role(Role.PARTNER)
                .file(new MockMultipartFile(TestConstants.TEST_FILE_NAME, mockByteArr))
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .categoryId(1L)
                .build();
        Business actualBusiness = businessServiceMock.updateBusiness(mockedBusinessRequest, 1L);
        Assertions.assertEquals(mockedBusiness, actualBusiness);
    }
}
