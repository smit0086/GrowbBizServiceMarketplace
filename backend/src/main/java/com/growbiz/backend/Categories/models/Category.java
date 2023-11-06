package com.growbiz.backend.Categories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.User.models.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;

    @NotBlank
    private String name;

    @NotBlank
    private String tax;

    @OneToOne(mappedBy = "category")
    @JsonIgnore
    private Business business;

}
