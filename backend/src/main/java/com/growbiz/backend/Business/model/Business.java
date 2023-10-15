package com.growbiz.backend.Business.model;

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

    private Long categoryId;

    private String fileURL;

}
