package com.growbiz.backend.Services.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Categories.models.SubCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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

    @NotBlank
    private LocalTime timeRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "businessId", referencedColumnName = "businessId")
    @JsonIgnore
    private Business business;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategoryId", referencedColumnName = "id")
    @JsonIgnore
    private SubCategory subCategory;
}
