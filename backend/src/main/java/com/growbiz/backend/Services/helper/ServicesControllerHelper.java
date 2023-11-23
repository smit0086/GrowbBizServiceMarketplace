package com.growbiz.backend.Services.helper;

import com.growbiz.backend.RequestResponse.Services.ServiceResponse;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import com.growbiz.backend.ReviewsAndRatings.service.IReviewsAndRatingsService;
import com.growbiz.backend.Services.models.ServiceDTO;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServicesControllerHelper {
    private IReviewsAndRatingsService reviewsAndRatingsService;

    public List<Double> getAvgRatingList(List<Services> serviceList) {
        List<Double> avgRatingList = new ArrayList<>();

        for (Services service:serviceList) {
            avgRatingList.add(getAvgRatingForEachService(service));
        }

        return avgRatingList;
    }

    public double getAvgRatingForEachService(Services service) {
        double sum = 0;
        List<ReviewsAndRatings> reviewsAndRatings = reviewsAndRatingsService.getReviewsAndRatingsByServiceId(service.getServiceId());
        for (ReviewsAndRatings reviewAndRating: reviewsAndRatings) {
            sum += reviewAndRating.getRating();
        }
        double avgRating = sum /reviewsAndRatings.size();

        return avgRating;
    }

    public ResponseEntity<ServiceResponse> createServiceResponse(List<Services> servicesList, Boolean isUpdated) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .services(servicesList)
                .isUpdated(isUpdated)
                .subject(user.getEmail())
                .role(user.getRole())
                .avgRatings(getAvgRatingList(servicesList))
                .build());
    }

    public ResponseEntity<ServiceResponse> createServiceDTOResponse(List<ServiceDTO> servicesList, Boolean isUpdated) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .serviceDTOS(servicesList)
                .isUpdated(isUpdated)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public ResponseEntity<ServiceResponse> deleteServiceResponse(Boolean deleted) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .isDeleted(deleted)
                .subject(user.getEmail())
                .role(user.getRole())
                .build());
    }

    public ResponseEntity<ServiceResponse> createServiceResponseWithTax(List<Services> service, String tax) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(ServiceResponse.builder()
                .services(service)
                .subject(user.getEmail())
                .role(user.getRole())
                .tax(tax)
                .avgRatings(getAvgRatingList(service))
                .businessId(service.get(0).getBusiness().getBusinessId())
                .build());
    }
}

