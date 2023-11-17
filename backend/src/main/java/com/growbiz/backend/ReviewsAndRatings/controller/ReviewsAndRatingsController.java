package com.growbiz.backend.ReviewsAndRatings.controller;

import com.growbiz.backend.Exception.exceptions.ReviewAndRatingNotFoundException;
import com.growbiz.backend.Exception.exceptions.ServiceAlreadyExistsException;
import com.growbiz.backend.Exception.exceptions.ServiceNotFoundException;
import com.growbiz.backend.ReviewsAndRatings.helper.ReviewsAndRatingsControllerHelper;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatingsRequest;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatingsResponse;
import com.growbiz.backend.ReviewsAndRatings.service.ReviewsAndRatingsService;
import com.growbiz.backend.Services.models.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewsAndRatingsController {

    @Autowired
    private ReviewsAndRatingsControllerHelper reviewsAndRatingsHelper;

    @Autowired
    private ReviewsAndRatingsService reviewsAndRatingsService;

    @GetMapping(path = "/allReviewsAndRatings")
    public ResponseEntity<ReviewsAndRatingsResponse> getAllReviewsAndRatings() {
        return reviewsAndRatingsHelper.createReviewsAndRatingsResponse(reviewsAndRatingsService.fetchReviewsAndRatingsList(), false);
    }

    @RequestMapping(path = "/addReviewAndRating")
    public ResponseEntity<ReviewsAndRatingsResponse> addReviewAndRating(@RequestBody ReviewsAndRatingsRequest addRequest) {
        ReviewsAndRatings reviewAndRating = reviewsAndRatingsService.addReviewAndRating(addRequest);
        if (reviewAndRating != null) {
            return reviewsAndRatingsHelper.createReviewsAndRatingsResponse(List.of(reviewAndRating), false);
        } else {
            throw new NullPointerException();
        }
    }

    @RequestMapping(path = "/updateReviewAndRating")
    public ResponseEntity<ReviewsAndRatingsResponse> updateReviewAndRating(@RequestBody ReviewsAndRatingsRequest updateRequest) {
        ReviewsAndRatings reviewAndRating = reviewsAndRatingsService.updateReviewAndRating(updateRequest);
        if (reviewAndRating != null) {
            return reviewsAndRatingsHelper.createReviewsAndRatingsResponse(List.of(reviewAndRating), true);
        } else {
            throw new ReviewAndRatingNotFoundException("The specified review and rating for update in not found");
        }
    }

    @DeleteMapping(path = "/deleteReviewAndRating")
    public ResponseEntity<ReviewsAndRatingsResponse> deleteReviewAndRating(@RequestBody ReviewsAndRatings oldReviewAndRating) {
        Boolean isDeleted = reviewsAndRatingsService.deleteReviewAndRating(oldReviewAndRating.getReviewAndRatingID());

        if (isDeleted) {
            return reviewsAndRatingsHelper.deleteReviewsAndRatingsResponse(true);
        } else {
            throw new ReviewAndRatingNotFoundException("The specified review and rating for deletion in not found");
        }
    }
}
