package com.growbiz.backend.Business.repository;

import com.growbiz.backend.Business.model.Business;
import com.growbiz.backend.Business.model.BusinessStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IBusinessRepository extends CrudRepository<Business, Long> {
    Business findByEmail(String email);

    List<Business> findByStatusEquals(BusinessStatus status);
}
