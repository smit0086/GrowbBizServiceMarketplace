package com.growbiz.backend.ReviewsAndRatings;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.ReviewsAndRatings.ReviewsAndRatingsRequest;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.repository.IReviewsAndRatingsRepository;
import com.growbiz.backend.ReviewsAndRatings.service.ReviewsAndRatingsService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewsAndRatingsServiceTest {
    @InjectMocks
    private ReviewsAndRatingsService reviewsAndRatingsService;

    @Mock
    private IReviewsAndRatingsRepository iReviewsAndRatingsRepository;

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private IServicesService iServicesService;

    @Mock
    User mockUser;

    @Mock
    Category mockCategory;

    @Mock
    SubCategory mockSubCategory;
    @Mock
    Business mockBusiness;

    @Mock
    Services mockService;

    @Mock
    ReviewsAndRatings mockReviewsAndRatings;

    @Mock
    ReviewsAndRatings mockUpdatedReviewsAndRatings;

    @Mock
    ReviewsAndRatingsRequest mockReviewsAndRatingsRequest;

    @Mock
    ReviewsAndRatingsRequest mockReviewsAndRatingsUpdateRequest;

    @BeforeEach
    public void init(){
        mockUser = User
                .builder()
                .id(1L)
                .email(TestConstants.TEST_EMAIL)
                .password(TestConstants.TEST_PASSWORD)
                .firstName(TestConstants.TEST_NAME)
                .lastName(TestConstants.TEST_NAME)
                .role(Role.CUSTOMER)
                .build();

        mockCategory = Category
                .builder()
                .categoryID(1L)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();

        mockSubCategory = SubCategory
                .builder()
                .subCategoryID(1L)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        mockBusiness = Business
                .builder()
                .businessId(1L)
                .businessName(TestConstants.TEST_BUSINESS_NAME)
                .email(TestConstants.TEST_EMAIL)
                .build();

        mockService = Services
                .builder()
                .serviceId(1L)
                .serviceName(TestConstants.TEST_SERVICE_NAME)
                .description(TestConstants.TEST_SERVICE_DESCRIPTION)
                .price(TestConstants.TEST_SERVICE_PRICE)
                .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
                .business(mockBusiness)
                .subCategory(mockSubCategory)
                .build();

        mockReviewsAndRatings = ReviewsAndRatings
                .builder()
                .reviewAndRatingID(1L)
                .review(TestConstants.TEST_REVIEW)
                .rating(TestConstants.TEST_RATING)
                .userEmail(mockUser.getEmail())
                .user(mockUser)
                .service(mockService)
                .build();

        mockUpdatedReviewsAndRatings = ReviewsAndRatings
                .builder()
                .reviewAndRatingID(1L)
                .review(TestConstants.TEST_REVIEW)
                .rating(TestConstants.TEST_RATING)
                .userEmail(mockUser.getEmail())
                .user(mockUser)
                .service(mockService)
                .build();

        mockReviewsAndRatingsRequest = ReviewsAndRatingsRequest
                .builder()
                .reviewAndRatingId(1L)
                .review(TestConstants.TEST_REVIEW)
                .rating(TestConstants.TEST_RATING)
                .userId(mockUser.getId())
                .serviceId(mockService.getServiceId())
                .userEmail(mockUser.getEmail())
                .build();

        mockReviewsAndRatingsUpdateRequest = ReviewsAndRatingsRequest
                .builder()
                .reviewAndRatingId(1L)
                .review(TestConstants.TEST_REVIEW)
                .rating(TestConstants.TEST_RATING)
                .userId(mockUser.getId())
                .serviceId(mockService.getServiceId())
                .userEmail(mockUser.getEmail())
                .build();
    }

    @Test
    public void getReviewAndRatingByIdTest(){
        ReviewsAndRatings actualResponse;
        ReviewsAndRatings expectedResponse = mockReviewsAndRatings;

        when(iReviewsAndRatingsRepository.findById(mockReviewsAndRatings.getReviewAndRatingID())).thenReturn(Optional.of(mockReviewsAndRatings));

        actualResponse = reviewsAndRatingsService.getReviewAndRatingById(mockReviewsAndRatings.getReviewAndRatingID());
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void getReviewAndRatingByIdNullTest(){
        ReviewsAndRatings actualResponse;

        when(iReviewsAndRatingsRepository.findById(mockReviewsAndRatings.getReviewAndRatingID())).thenReturn(Optional.empty());

        actualResponse = reviewsAndRatingsService.getReviewAndRatingById(mockReviewsAndRatings.getReviewAndRatingID());
        assertNull(actualResponse);
    }

    @Test
    public void getReviewsAndRatingsByServiceIdTest(){
        List<ReviewsAndRatings> actualResponse;
        List<ReviewsAndRatings> expectedResponse = List.of(mockReviewsAndRatings);

        when(iReviewsAndRatingsRepository.findByServiceServiceId(mockService.getServiceId())).thenReturn(List.of(mockReviewsAndRatings));

        actualResponse = reviewsAndRatingsService.getReviewsAndRatingsByServiceId(mockService.getServiceId());
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void getReviewsAndRatingsByServiceIdExceptionTest(){
        List<ReviewsAndRatings> actualResponse;

        when(iReviewsAndRatingsRepository.findByServiceServiceId(mockService.getServiceId())).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = reviewsAndRatingsService.getReviewsAndRatingsByServiceId(mockService.getServiceId());
        assertNull(actualResponse);
    }

    @Test
    public void fetchReviewsAndRatingsListTest(){
        List<ReviewsAndRatings> actualResponse;
        List<ReviewsAndRatings> expectedResponse = List.of(mockReviewsAndRatings);

        when(iReviewsAndRatingsRepository.findAll()).thenReturn(List.of(mockReviewsAndRatings));

        actualResponse = reviewsAndRatingsService.fetchReviewsAndRatingsList();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void fetchReviewsAndRatingsListExceptionTest(){
        List<ReviewsAndRatings> actualResponse;

        when(iReviewsAndRatingsRepository.findAll()).thenThrow(new RuntimeException("Test Exception"));

        actualResponse = reviewsAndRatingsService.fetchReviewsAndRatingsList();
        assertNull(actualResponse);
    }

    @Test
    public void addReviewAndRatingTest(){
        ReviewsAndRatings actualResponse;
        ReviewsAndRatings expectedResponse = mockReviewsAndRatings;

        when(iReviewsAndRatingsRepository.findByServiceServiceIdAndUserId(mockReviewsAndRatingsRequest.getServiceId(), mockReviewsAndRatingsRequest.getUserId())).thenReturn(null);
        when(iUserRepository.findById(mockReviewsAndRatingsRequest.getUserId())).thenReturn(Optional.of(mockUser));
        when(iServicesService.getServiceById(mockReviewsAndRatingsRequest.getServiceId())).thenReturn(mockService);
        when(iReviewsAndRatingsRepository.save(any(ReviewsAndRatings.class))).thenReturn(mockReviewsAndRatings);

        actualResponse = reviewsAndRatingsService.addReviewAndRating(mockReviewsAndRatingsRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addReviewAndRatingNullTest(){
        ReviewsAndRatings actualResponse;

        when(iReviewsAndRatingsRepository.findByServiceServiceIdAndUserId(mockReviewsAndRatingsRequest.getServiceId(), mockReviewsAndRatingsRequest.getUserId())).thenReturn(mockReviewsAndRatings);

        actualResponse = reviewsAndRatingsService.addReviewAndRating(mockReviewsAndRatingsRequest);
        assertNull(actualResponse);
    }

    @Test
    public void updateReviewAndRatingTest(){
        ReviewsAndRatings actualResponse;
        ReviewsAndRatings expectedResponse = mockUpdatedReviewsAndRatings;

        when(iReviewsAndRatingsRepository.findById(mockReviewsAndRatingsUpdateRequest.getReviewAndRatingId())).thenReturn(Optional.of(mockReviewsAndRatings));
        when(iUserRepository.findById(mockReviewsAndRatingsUpdateRequest.getUserId())).thenReturn(Optional.of(mockUser));
        when(iServicesService.getServiceById(mockReviewsAndRatingsUpdateRequest.getServiceId())).thenReturn(mockService);
        when(iReviewsAndRatingsRepository.save(any(ReviewsAndRatings.class))).thenReturn(mockUpdatedReviewsAndRatings);

        actualResponse = reviewsAndRatingsService.updateReviewAndRating(mockReviewsAndRatingsUpdateRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateReviewAndRatingNullTest(){
        ReviewsAndRatings actualResponse;

        when(iReviewsAndRatingsRepository.findById(mockReviewsAndRatingsRequest.getReviewAndRatingId())).thenReturn(Optional.empty());

        actualResponse = reviewsAndRatingsService.updateReviewAndRating(mockReviewsAndRatingsRequest);
        assertNull(actualResponse);
    }

    @Test
    public void deleteReviewAndRatingTest(){
        Boolean actualResponse;
        Boolean expectedResponse = true;

        doNothing().when(iReviewsAndRatingsRepository).deleteById(any());

        actualResponse = reviewsAndRatingsService.deleteReviewAndRating(mockReviewsAndRatings.getReviewAndRatingID());
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteReviewAndRatingExceptionTest(){
        Boolean actualResponse;
        Boolean expectedResponse = false;

        doThrow(new RuntimeException("Test Exception")).when(iReviewsAndRatingsRepository).deleteById(any());

        actualResponse = reviewsAndRatingsService.deleteReviewAndRating(mockReviewsAndRatings.getReviewAndRatingID());
        assertEquals(actualResponse, expectedResponse);
    }
}
