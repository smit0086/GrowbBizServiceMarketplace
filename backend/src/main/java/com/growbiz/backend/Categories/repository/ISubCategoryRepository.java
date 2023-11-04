package com.growbiz.backend.Categories.repository;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Services.models.Services;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends CrudRepository<SubCategory, Long> {

//    List<Services> findByCategoryCategoryID(Long categoryID);

}
