package com.growbiz.backend.ReviewsAndRatings.repository;

import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewsAndRatingsRepository extends CrudRepository<ReviewsAndRatings, Long> {

    ReviewsAndRatings findByServiceIdAndUserId(Long serviceId, Long userId);

    List<ReviewsAndRatings> findByUserId(Long userId);

    List<ReviewsAndRatings> findByServiceServiceId(Long serviceId);
}
