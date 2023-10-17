package com.growbiz.backend.Business.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BusinessHour {

    @Id
    private Long businessId;

    private Time monday_start;
    private Time monday_end;
    private Time tuesday_start;
    private Time tuesday_end;
    private Time wednesday_start;
    private Time wednesday_end;
    private Time thursday_start;
    private Time thursday_end;
    private Time friday_start;
    private Time friday_end;
    private Time saturday_start;
    private Time saturday_end;
    private Time sunday_start;
    private Time sunday_end;
}
