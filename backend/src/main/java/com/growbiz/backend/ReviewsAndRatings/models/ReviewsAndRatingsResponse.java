package com.growbiz.backend.ReviewsAndRatings.models;

import com.growbiz.backend.Responses.model.BasicResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ReviewsAndRatingsResponse extends BasicResponse {
    private List<ReviewsAndRatings> reviewsAndRatings;
    private Long businessId;
    private Boolean isUpdated;
    private Boolean isDeleted;
}
