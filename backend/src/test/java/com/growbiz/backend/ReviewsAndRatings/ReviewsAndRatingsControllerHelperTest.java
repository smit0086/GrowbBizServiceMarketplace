package com.growbiz.backend.ReviewsAndRatings;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Enums.Role;
import com.growbiz.backend.RequestResponse.ReviewsAndRatings.ReviewsAndRatingsResponse;
import com.growbiz.backend.ReviewsAndRatings.helper.ReviewsAndRatingsControllerHelper;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.TestConstants.TestConstants;
import com.growbiz.backend.User.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewsAndRatingsControllerHelperTest {
    @InjectMocks
    private ReviewsAndRatingsControllerHelper reviewsAndRatingsControllerHelper;

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

    @Test
    public void createReviewsAndRatingsResponseTest(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .reviewsAndRatings(List.of(mockReviewsAndRatings))
                .isUpdated(false)
                .isDeleted(false)
                .build());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        actualResponse = reviewsAndRatingsControllerHelper.createReviewsAndRatingsResponse(List.of(mockReviewsAndRatings), false);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    public void deleteReviewsAndRatingsResponse(){
        ResponseEntity<ReviewsAndRatingsResponse> actualResponse;
        ResponseEntity<ReviewsAndRatingsResponse> expectedResponse = ResponseEntity.ok(ReviewsAndRatingsResponse
                .builder()
                .isDeleted(true)
                .subject(mockUser.getEmail())
                .role(mockUser.getRole())
                .build());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        actualResponse = reviewsAndRatingsControllerHelper.deleteReviewsAndRatingsResponse(true);
        assertEquals(actualResponse, expectedResponse);
    }
}
