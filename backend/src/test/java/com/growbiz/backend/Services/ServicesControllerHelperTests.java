package com.growbiz.backend.Services;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.Services.ServiceResponse;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.service.IReviewsAndRatingsService;
import com.growbiz.backend.Services.helper.ServicesControllerHelper;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicesControllerHelperTests {

    @InjectMocks
    private ServicesControllerHelper servicesControllerHelper;

    @Mock
    private IReviewsAndRatingsService reviewsAndRatingsService;


    User mockUser;
    Authentication authentication;
    SecurityContext securityContext;
    Business mockBusiness;

    Category mockCategory;

    SubCategory mockSubCategory;
    Services mockService;
    ReviewsAndRatings mockReviewsAndRatings;

    ServiceDTO mockServiceDTO;

    @BeforeEach
    public void init() {
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        mockUser = User
                .builder()
                .id(TestConstants.TEST_ID_1)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName("John")
                .lastName("Doe")
                .role(Role.CUSTOMER)
                .build();
        mockServiceDTO = ServiceDTO
                .builder()
                .serviceId(TestConstants.TEST_ID_1)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .businessId(TestConstants.TEST_ID_1)
                .subCategoryId(TestConstants.TEST_ID_1)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .build();
        mockCategory = Category
                .builder()
                .categoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(TestConstants.TEST_ID_1)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        mockBusiness = Business
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
                .subCategory(mockSubCategory)
                .build();
        mockReviewsAndRatings = ReviewsAndRatings
                .builder()
                .service(mockService)
                .user(mockUser)
                .rating(TestConstants.TEST_AVG_RATING)
                .userEmail(TestConstants.TEST_EMAIL)
                .review(TestConstants.TEST_SERVICE_DESCRIPTION)
                .build();
    }

//    @Test
//    void testGetAvgRatingList() {
//        Services service1 = mock(Services.class);
//        Services service2 = mock(Services.class);
//        List<Services> serviceList = Arrays.asList(service1, service2);
//
//        ReviewsAndRatings review1 = mock(ReviewsAndRatings.class);
//        ReviewsAndRatings review2 = mock(ReviewsAndRatings.class);
//        ReviewsAndRatings review3 = mock(ReviewsAndRatings.class);
//
//        List<ReviewsAndRatings> reviewsAndRatingsList1 = Arrays.asList(review1);
//        List<ReviewsAndRatings> reviewsAndRatingsList2 = Arrays.asList(review2, review3);
//
//        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_1)).thenReturn(reviewsAndRatingsList1);
//        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_2)).thenReturn(reviewsAndRatingsList2);
//
//        List<Double> avgRatingList = servicesControllerHelper.getAvgRatingList(serviceList);
//
//        assertEquals(Arrays.asList(5.0, 3.5), avgRatingList);
//    }

//    @Test
//    void testGetAvgRatingForEachService() {
//        // Mocking the ReviewsAndRatingsService
//        ReviewsAndRatingsService reviewsAndRatingsService = Mockito.mock(ReviewsAndRatingsService.class);
//
//        // Creating an instance of YourService and injecting the mock service
//        YourService yourService = new YourService(reviewsAndRatingsService);
//
//        // Mock data for the test
//        Services service = ;
//        ReviewsAndRatings review1 = new ReviewsAndRatings(1, 5.0);
//        ReviewsAndRatings review2 = new ReviewsAndRatings(1, 3.0);
//        List<ReviewsAndRatings> reviewsAndRatingsList = Arrays.asList(review1, review2);
//
//        // Setting up mock behavior
//        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(1)).thenReturn(reviewsAndRatingsList);
//
//        // Calling the method to test
//        double avgRating = yourService.getAvgRatingForEachService(service);
//
//        // Assertions
//        assertEquals(4.0, avgRating);
//    }


    @Test
    public void createServiceResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .services(List.of(mockService))
                        .isUpdated(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .avgRatings(List.of(TestConstants.TEST_AVG_RATING))
                        .build()
        );
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(mockService.getServiceId())).thenReturn(List.of(mockReviewsAndRatings));
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceResponse(List.of(mockService),true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createServiceDTOResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .serviceDTOS(List.of(mockServiceDTO))
                        .isUpdated(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceDTOResponse(List.of(mockServiceDTO),true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void deleteServiceResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .isDeleted(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.deleteServiceResponse(true);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void createServiceResponseWithTaxTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .services(List.of(mockService))
                        .tax(TestConstants.TEST_CATEGORY_TAX)
                        .businessId(mockBusiness.getBusinessId())
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .avgRatings(List.of(TestConstants.TEST_AVG_RATING))
                        .build()
        );
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockReviewsAndRatings));
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        ResponseEntity<ServiceResponse> actualResponse = servicesControllerHelper.createServiceResponseWithTax(List.of(mockService),TestConstants.TEST_CATEGORY_TAX);
        assertEquals(expectedResponse, actualResponse);
    }
}
