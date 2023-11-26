package com.growbiz.backend.ReviewsAndRatings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growbiz.backend.Services.models.Services;
import com.growbiz.backend.User.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewsAndRatings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewAndRatingID;

//    @NotBlank
    private String review;

//    @NotBlank
    private double rating;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "serviceId")
    @JsonIgnore
    private Services service;
}
