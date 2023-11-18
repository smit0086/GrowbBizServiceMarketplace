package com.growbiz.backend.ReviewsAndRatings.models;

import com.growbiz.backend.Responses.model.BasicRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReviewsAndRatingsRequest extends BasicRequest {

    private long reviewAndRatingId;
    private String review;
    private double rating;
    private long userId;
    private long serviceId;
}

