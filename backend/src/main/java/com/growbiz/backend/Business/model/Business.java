package com.growbiz.backend.Business.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growbiz.backend.Categories.models.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    private String businessName;

    @Email
    @Column(unique = true)
    private String email;

    private BusinessStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryID")
    @JsonIgnore
    private Category category;

    private String fileURL;

    private String description;

    private String reason;

}
