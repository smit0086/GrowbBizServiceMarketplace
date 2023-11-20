package com.growbiz.backend.BusinessHour.repository;

import com.growbiz.backend.BusinessHour.model.BusinessHour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusinessHourRepository extends CrudRepository<BusinessHour, Long> {

}
