package com.growbiz.backend.Business;

import com.growbiz.backend.Business.helper.BusinessServiceHelper;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Business.repository.IBusinessRepository;
import com.growbiz.backend.Business.service.BusinessService;
import com.growbiz.backend.BusinessHour.service.IBusinessHourService;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.service.Super.ICategoryService;
import com.growbiz.backend.Enums.BusinessStatus;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Exception.exceptions.Business.BusinessAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.Business.BusinessNotFoundException;
import com.growbiz.backend.RequestResponse.Business.BusinessRequest;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    private ICategoryService categoryServiceMock;
    @Mock
    private IBusinessHourService businessHourServiceMock;
    @Mock
    private BusinessServiceHelper helperMock;
    Business mockedBusiness;
    Category mockedCategory;
    @Mock
    Path path;

    @BeforeEach
    public void init() {
        //MockitoAnnotations.openMocks(this);
        mockedCategory = Category.builder().categoryID(1L).name("TestCategory").tax(TestConstants.TEST_CATEGORY_TAX).build();
        mockedBusiness = Business.builder().businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .description(TestConstants.TEST_BUSINESS_DESCRIPTION)
                .email(TestConstants.TEST_EMAIL)
                .fileURL(TestConstants.TEST_BUSINESS_FILE_PATH)
                .status(BusinessStatus.PENDING)
                .category(mockedCategory)
                .build();
    }

    @Test
    public void testFetchBusinesses() {
        when(businessRepositoryMock.findByStatusEquals(BusinessStatus.PENDING)).thenReturn(List.of(mockedBusiness));
        when(businessRepositoryMock.findAll()).thenReturn(List.of(mockedBusiness));
        List<Business> listOfBusinessWhenStatus = businessServiceMock.fetchBusinesses(BusinessStatus.PENDING.name());
        List<Business> listOfBusinessWhenStatusIsNull = businessServiceMock.fetchBusinesses(null);
        Assertions.assertEquals(List.of(mockedBusiness), listOfBusinessWhenStatusIsNull);
        Assertions.assertEquals(List.of(mockedBusiness), listOfBusinessWhenStatus);
    }

    @Test
    public void testFindById() {
        when(businessRepositoryMock.findById(1L)).thenReturn(Optional.of(mockedBusiness));
        Business actualBusinessWithCorrectId = businessServiceMock.findById(1L);
        Assertions.assertEquals(mockedBusiness, actualBusinessWithCorrectId);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> businessServiceMock.findById(2L));
    }

    @Test
    public void testFindByEmail() {
        when(businessRepositoryMock.findByEmail(TestConstants.TEST_EMAIL)).thenReturn(mockedBusiness);
        Business actualBusinessWithCorrectEmail = businessServiceMock.findByEmail(TestConstants.TEST_EMAIL);
        Assertions.assertEquals(mockedBusiness, actualBusinessWithCorrectEmail);
        Assertions.assertThrows(BusinessNotFoundException.class, () -> businessServiceMock.findByEmail("testEmailIncorrect@dal.ca"));
    }

    @Test
    public void testSaveWithBusinessRequest() {
        when(helperMock.uploadFileToStorage(any(MultipartFile.class), Mockito.eq(TestConstants.TEST_EMAIL)))
                .thenReturn(TestConstants.TEST_BUSINESS_FILE_PATH);
        doNothing().when(businessHourServiceMock).init(anyLong());
        when(categoryServiceMock.getCategoryByID(1L)).thenReturn(mockedCategory);
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
    void testBusinessAlreadyExistsException() {
        when(businessRepositoryMock.findByEmail(TestConstants.TEST_EMAIL)).thenReturn(mockedBusiness);
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
    void testSave() {
        businessServiceMock.save(mockedBusiness);
    }

    @Test
    void testUpdateBusiness() {
        when(helperMock.uploadFileToStorage(any(MultipartFile.class), Mockito.eq(TestConstants.TEST_EMAIL)))
                .thenReturn(TestConstants.TEST_BUSINESS_FILE_PATH);
        when(categoryServiceMock.getCategoryByID(1L)).thenReturn(mockedCategory);
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

    @Test
    void testDownloadFile() throws IOException {
        mockStatic(Files.class);
        mockStatic(Paths.class);
        Path mockedPath = mock(Path.class);
        File mockedFile = mock(File.class);
        byte[] mockedFileContent = "Mocked file content".getBytes();
        when(Paths.get(anyString())).thenReturn(path);
        when(Files.list(any(Path.class))).thenReturn(Stream.of(mockedPath));
        when(mockedPath.toFile()).thenReturn(mockedFile);
        when(mockedFile.isFile()).thenReturn(true);
        when(mockedFile.toPath()).thenReturn(mockedPath);
        when(Files.readAllBytes(any(Path.class))).thenReturn(mockedFileContent);
        when(businessRepositoryMock.findByEmail(TestConstants.TEST_EMAIL)).thenReturn(mockedBusiness);
        byte[] actualValue = businessServiceMock.downloadFile(TestConstants.TEST_EMAIL);
        Assertions.assertEquals(mockedFileContent, actualValue);
    }
}
