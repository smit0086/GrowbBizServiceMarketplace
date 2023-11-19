package com.growbiz.backend.ReviewsAndRatings.service;

import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatingsRequest;
import com.growbiz.backend.ReviewsAndRatings.repository.IReviewsAndRatingsRepository;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.Services.service.IServicesService;
import com.growbiz.backend.User.models.User;
import com.growbiz.backend.User.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewsAndRatingsService implements IReviewsAndRatingsService {

    @Autowired
    private final IReviewsAndRatingsRepository iReviewsAndRatingsRepository;

    @Autowired
    private final IServicesService iServicesService;

    @Autowired
    private final IUserRepository iUserRepository;

    @Override
    public ReviewsAndRatings getReviewAndRatingById(Long reviewAndRatingId) {
        Optional<ReviewsAndRatings> reviewAndRating = iReviewsAndRatingsRepository.findById(reviewAndRatingId);
        if (reviewAndRating.isPresent()) {
            return reviewAndRating.get();
        } else {
            return null;
        }
    }

    @Override
    public List<ReviewsAndRatings> getReviewsAndRatingsByServiceId(Long serviceId) {
        try {
            return iReviewsAndRatingsRepository.findByServiceServiceId(serviceId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ReviewsAndRatings> fetchReviewsAndRatingsList() {
        try {
            return (List<ReviewsAndRatings>) iReviewsAndRatingsRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ReviewsAndRatings addReviewAndRating(ReviewsAndRatingsRequest newReviewsAndRatingsRequest) {
        ReviewsAndRatings existingReviewAndRating = iReviewsAndRatingsRepository.findByServiceServiceIdAndUserId(newReviewsAndRatingsRequest.getServiceId(), newReviewsAndRatingsRequest.getUserId());

        if (Objects.isNull(existingReviewAndRating)) {
            User user = iUserRepository.findById(newReviewsAndRatingsRequest.getUserId()).get();
            Services service = iServicesService.getServiceById(newReviewsAndRatingsRequest.getServiceId());
            ReviewsAndRatings reviewAndRatingUpdated = ReviewsAndRatings.builder()
                    .review(newReviewsAndRatingsRequest.getReview())
                    .rating(newReviewsAndRatingsRequest.getRating())
                    .user(user)
                    .userEmail(user.getEmail())
                    .service(service)
                    .build();
            return iReviewsAndRatingsRepository.save(reviewAndRatingUpdated);
        }

        return null;
    }

    @Override
    public ReviewsAndRatings updateReviewAndRating(ReviewsAndRatingsRequest reviewsAndRatingsRequest) {
        Optional<ReviewsAndRatings> reviewAndRatingToUpdate = iReviewsAndRatingsRepository.findById(reviewsAndRatingsRequest.getReviewAndRatingId());
        if (reviewAndRatingToUpdate.isEmpty()) {
            return null;
        } else {
            User user = iUserRepository.findById(reviewsAndRatingsRequest.getUserId()).get();
            Services service = iServicesService.getServiceById(reviewsAndRatingsRequest.getServiceId());
            ReviewsAndRatings reviewAndRatingUpdated = ReviewsAndRatings.builder()
                    .reviewAndRatingID(reviewAndRatingToUpdate.get().getReviewAndRatingID())
                    .review(reviewsAndRatingsRequest.getReview())
                    .rating(reviewsAndRatingsRequest.getRating())
                    .user(user)
                    .userEmail(user.getEmail())
                    .service(service)
                    .build();
            return iReviewsAndRatingsRepository.save(reviewAndRatingUpdated);
        }
    }

    @Override
    public Boolean deleteReviewAndRating(Long reviewAndRatingID) {
        try {
            iReviewsAndRatingsRepository.deleteById(reviewAndRatingID);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
