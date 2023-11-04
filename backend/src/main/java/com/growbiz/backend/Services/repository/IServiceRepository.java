package com.growbiz.backend.Services.repository;

import com.growbiz.backend.Services.models.Services;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IServiceRepository extends CrudRepository<Services, Long> {

    //@Query("FROM Service s WHERE s.business.id = :businessId")
    List<Services> findByBusinessBusinessId(Long businessId);

//    @Query("FROM Service b WHERE b.subCategory.id = :subCategoryId")
    List<Services> findBySubCategorySubCategoryID(Long subCategoryId);
}
