package com.growbiz.backend.Services.repository;

import com.growbiz.backend.Services.models.Services;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceRepository extends CrudRepository<Services, Long> {

    Services findByServiceNameAndBusinessBusinessId(String serviceName, Long businessId);

    List<Services> findByBusinessBusinessId(Long businessId);

    List<Services> findBySubCategorySubCategoryID(Long subCategoryId);
}
