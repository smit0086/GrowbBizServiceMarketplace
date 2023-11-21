package com.growbiz.backend.ReviewsAndRatings;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.Exception.exceptions.ReviewAndRating.ReviewAndRatingAlreadyExists;
import com.growbiz.backend.RequestResponse.ReviewsAndRatings.ReviewsAndRatingsRequest;
import com.growbiz.backend.RequestResponse.ReviewsAndRatings.ReviewsAndRatingsResponse;
import com.growbiz.backend.ReviewsAndRatings.controller.ReviewsAndRatingsController;
import com.growbiz.backend.ReviewsAndRatings.helper.ReviewsAndRatingsControllerHelper;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.service.ReviewsAndRatingsService;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewsAndRatingsControllerTest {
    @InjectMocks
    private ReviewsAndRatingsController reviewsAndRatingsController;

    @Mock
    private ReviewsAndRatingsControllerHelper reviewsAndRatingsControllerHelper;

    @Mock
    private ReviewsAndRatingsService reviewsAndRatingsService;

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    User mockUser = User
            .builder()
            .id(1L)
            .email(TestConstants.TEST_EMAIL)
            .password(TestConstants.TEST_PASSWORD)
            .firstName(TestConstants.TEST_NAME)
            .lastName(TestConstants.TEST_NAME)
            .role(Role.CUSTOMER)
            .build();

    @Mock
    Category mockCategory = Category
            .builder()
            .categoryID(1L)
            .name(TestConstants.TEST_CATEGORY_NAME)
            .tax(TestConstants.TEST_CATEGORY_TAX)
            .build();

    @Mock
    SubCategory mockSubCategory = SubCategory
            .builder()
            .subCategoryID(1L)
            .name(TestConstants.TEST_SUBCATEGORY_NAME)
            .category(mockCategory)
            .build();
    @Mock
    Business mockBusiness = Business
            .builder()
            .businessId(1L)
            .businessName(TestConstants.TEST_BUSINESS_NAME)
            .email(TestConstants.TEST_EMAIL)
            .build();

    @Mock
    Services mockService = Services
            .builder()
            .serviceId(1L)
            .serviceName(TestConstants.TEST_SERVICE_NAME)
            .description(TestConstants.TEST_SERVICE_DESCRIPTION)
            .price(TestConstants.TEST_SERVICE_PRICE)
            .imageURL(TestConstants.TEST_SERVICE_IMAGE_URL)
            .business(mockBusiness)
            .subCategory(mockSubCategory)
            .build();

    @Mock
    ReviewsAndRatings mockReviewsAndRatings = ReviewsAndRatings
            .builder()
            .reviewAndRatingID(1L)
            .review(TestConstants.TEST_REVIEW)
            .rating(TestConstants.TEST_RATING)
            .userEmail(mockUser.getEmail())
            .user(mockUser)
            .service(mockService)
            .build();

    @Mock
    ReviewsAndRatings mockUpdatedReviewsAndRatings = ReviewsAndRatings
            .builder()
            .reviewAndRatingID(1L)
            .review(TestConstants.TEST_REVIEW)
            .rating(TestConstants.TEST_RATING)
            .userEmail(mockUser.getEmail())
            .user(mockUser)
            .service(mockService)
            .build();

    @Mock
    ReviewsAndRatingsRequest mockReviewsAndRatingsRequest = ReviewsAndRatingsRequest
            .builder()
            .reviewAndRatingId(1L)
            .review(TestConstants.TEST_REVIEW)
            .rating(TestConstants.TEST_RATING)
            .userId(mockUser.getId())
            .serviceId(mockService.getServiceId())
            .userEmail(mockUser.getEmail())
            .build();

    @Mock
    ReviewsAndRatingsRequest mockReviewsAndRatingsUpdateRequest = ReviewsAndRatingsRequest
            .builder()
            .reviewAndRatingId(1L)
            .review(TestConstants.TEST_REVIEW)
            .rating(TestConstants.TEST_RATING)
            .userId(mockUser.getId())
            .serviceId(mockService.getServiceId())
            .userEmail(mockUser.getEmail())
            .build();

    @Test
    public void getAllReviewsAndRatingsTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .reviewsAndRatings(List.of(mockReviewsAndRatings))
                .isUpdated(false)
                .isDeleted(false)
                .build());

        when(reviewsAndRatingsService.fetchReviewsAndRatingsList()).thenReturn(List.of(mockReviewsAndRatings));
        when(reviewsAndRatingsControllerHelper.createReviewsAndRatingsResponse(List.of(mockReviewsAndRatings), false)).thenReturn(expectedResponse);

        actualResponse = reviewsAndRatingsController.getAllReviewsAndRatings();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void getAllReviewsAndRatingsByServiceIdTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .reviewsAndRatings(List.of(mockReviewsAndRatings))
                .isUpdated(false)
                .isDeleted(false)
                .build());

        when(reviewsAndRatingsService.getReviewsAndRatingsByServiceId(mockService.getServiceId())).thenReturn(List.of(mockReviewsAndRatings));
        when(reviewsAndRatingsControllerHelper.createReviewsAndRatingsResponse(List.of(mockReviewsAndRatings), false)).thenReturn(expectedResponse);

        actualResponse = reviewsAndRatingsController.getAllReviewsAndRatingsByServiceId(mockService.getServiceId());
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void addReviewAndRatingTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .reviewsAndRatings(List.of(mockReviewsAndRatings))
                .isUpdated(false)
                .isDeleted(false)
                .build());

        when(reviewsAndRatingsService.addReviewAndRating(mockReviewsAndRatingsRequest)).thenReturn(mockReviewsAndRatings);
        when(iUserRepository.findById(mockReviewsAndRatingsRequest.getUserId())).thenReturn(Optional.of(mockUser));
        when(reviewsAndRatingsControllerHelper.createReviewsAndRatingsResponse(List.of(mockReviewsAndRatings), false)).thenReturn(expectedResponse);

        actualResponse = reviewsAndRatingsController.addReviewAndRating(mockReviewsAndRatingsRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void updateReviewAndRatingTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .reviewsAndRatings(List.of(mockUpdatedReviewsAndRatings))
                .isUpdated(true)
                .isDeleted(false)
                .build());

        when(reviewsAndRatingsService.updateReviewAndRating(mockReviewsAndRatingsUpdateRequest)).thenReturn(mockUpdatedReviewsAndRatings);
        when(iUserRepository.findById(mockReviewsAndRatingsUpdateRequest.getUserId())).thenReturn(Optional.of(mockUser));
        when(reviewsAndRatingsControllerHelper.createReviewsAndRatingsResponse(List.of(mockUpdatedReviewsAndRatings), true)).thenReturn(expectedResponse);

        actualResponse = reviewsAndRatingsController.updateReviewAndRating(mockReviewsAndRatingsUpdateRequest);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteReviewAndRatingTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .isDeleted(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        when(reviewsAndRatingsService.deleteReviewAndRating(mockReviewsAndRatings.getReviewAndRatingID())).thenReturn(true);
        when(reviewsAndRatingsControllerHelper.deleteReviewsAndRatingsResponse(true)).thenReturn(expectedResponse);

        actualResponse = reviewsAndRatingsController.deleteReviewAndRating(mockReviewsAndRatings);
        assertEquals(actualResponse, expectedResponse);
    }
}
