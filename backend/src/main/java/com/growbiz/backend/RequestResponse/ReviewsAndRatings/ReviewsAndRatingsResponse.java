package com.growbiz.backend.RequestResponse.ReviewsAndRatings;

import com.growbiz.backend.RequestResponse.Basic.BasicResponse;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ReviewsAndRatingsResponse extends BasicResponse {
    private List<ReviewsAndRatings> reviewsAndRatings;
    private Boolean isUpdated;
    private Boolean isDeleted;
}
