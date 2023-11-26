package com.growbiz.backend.Services.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growbiz.backend.Booking.models.Booking;
import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.ReviewsAndRatings.models.ReviewsAndRatings;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @NotBlank
    private String serviceName;

    @NotBlank
    private String description;

    @NotNull
    private Double price;

    @NotNull
    private LocalTime timeRequired;

    @ManyToOne
    @JoinColumn(name = "businessId", referencedColumnName = "businessId")
    @JsonIgnore
    private Business business;

    @ManyToOne
    @JoinColumn(name = "subCategoryID", referencedColumnName = "subCategoryID")
    @JsonIgnore
    private SubCategory subCategory;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ReviewsAndRatings> reviewsAndRatings;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;


    private String imageURL;

    private Double avgRating;
}
