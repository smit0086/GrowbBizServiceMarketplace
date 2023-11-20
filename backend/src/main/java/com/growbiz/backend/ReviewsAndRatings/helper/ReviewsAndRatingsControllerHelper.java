package com.growbiz.backend.ReviewsAndRatings.helper;

import com.growbiz.backend.RequestResponse.ReviewsAndRatings.ReviewsAndRatingsResponse;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewsAndRatingsControllerHelper {
    public ResponseEntity<ReviewsAndRatingsResponse> createReviewsAndRatingsResponse(List<ReviewsAndRatings> reviewsAndRatingsList, Boolean isUpdated) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ReviewsAndRatingsResponse.builder().reviewsAndRatings(reviewsAndRatingsList)
                .isUpdated(isUpdated)
                .isDeleted(false)
                .build());
    }

    public ResponseEntity<ReviewsAndRatingsResponse> deleteReviewsAndRatingsResponse(Boolean deleted) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ReviewsAndRatingsResponse.builder()
                .isDeleted(deleted)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }
}


