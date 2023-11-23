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
    Services mockService1;
    ReviewsAndRatings mockReviewsAndRatings;
    ReviewsAndRatings mockReviewsAndRatings1;
    ReviewsAndRatings mockReviewsAndRatings2;
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
        mockService1 = Services
                .builder()
                .serviceId(TestConstants.TEST_ID_2)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();
        mockReviewsAndRatings = ReviewsAndRatings
                .builder().reviewAndRatingID(TestConstants.TEST_ID_1)
                .service(mockService)
                .user(mockUser)
                .rating(TestConstants.TEST_AVG_RATING2)
                .userEmail(TestConstants.TEST_EMAIL)
                .review(TestConstants.TEST_SERVICE_DESCRIPTION)
                .build();
        mockReviewsAndRatings1 = ReviewsAndRatings
                .builder().reviewAndRatingID(TestConstants.TEST_ID_2)
                .service(mockService1)
                .user(mockUser)
                .rating(TestConstants.TEST_AVG_RATING1)
                .userEmail(TestConstants.TEST_EMAIL)
                .review(TestConstants.TEST_SERVICE_DESCRIPTION)
                .build();
        mockReviewsAndRatings2 = ReviewsAndRatings
                .builder().reviewAndRatingID(TestConstants.TEST_ID_3)
                .service(mockService)
                .user(mockUser)
                .rating(TestConstants.TEST_AVG_RATING2)
                .userEmail(TestConstants.TEST_EMAIL)
                .review(TestConstants.TEST_SERVICE_DESCRIPTION)
                .build();
    }

    @Test
    void testGetAvgRatingList() {
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockReviewsAndRatings2, mockReviewsAndRatings));
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_2)).thenReturn(List.of(mockReviewsAndRatings1));

        List<Double> avgRatingList = servicesControllerHelper.getAvgRatingList(List.of(mockService,mockService1));

        assertEquals(Arrays.asList(TestConstants.TEST_AVG_RATING2, TestConstants.TEST_AVG_RATING1), avgRatingList);
    }

    @Test
    void testGetAvgRatingForEachService() {
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(TestConstants.TEST_ID_1)).thenReturn(List.of(mockReviewsAndRatings2, mockReviewsAndRatings));

        double avgRating = servicesControllerHelper.getAvgRatingForEachService(mockService.getServiceId());

        assertEquals(TestConstants.TEST_AVG_RATING2, avgRating);
    }

    @Test
    public void createServiceResponseTest() {
        ResponseEntity<ServiceResponse> expectedResponse = ResponseEntity.ok(
                ServiceResponse.builder()
                        .services(List.of(mockService))
                        .isUpdated(true)
                        .subject(mockUser.getEmail())
                        .role(Role.CUSTOMER)
                        .avgRatings(List.of(TestConstants.TEST_AVG_RATING2))
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
                        .avgRatings(List.of(TestConstants.TEST_AVG_RATING2))
                        .build()
        );
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(mockServiceDTO.getServiceId())).thenReturn(List.of(mockReviewsAndRatings, mockReviewsAndRatings2));

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
                        .avgRatings(List.of(TestConstants.TEST_AVG_RATING2))
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
