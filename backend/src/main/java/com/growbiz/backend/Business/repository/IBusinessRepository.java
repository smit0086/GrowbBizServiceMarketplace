package com.growbiz.backend.Business.repository;

import com.growbiz.backend.Business.models.Business;
import com.growbiz.backend.Enums.BusinessStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBusinessRepository extends CrudRepository<Business, Long> {
    Business findByEmail(String email);

    List<Business> findByStatusEquals(BusinessStatus status);
}
