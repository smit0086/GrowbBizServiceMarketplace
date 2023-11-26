package com.growbiz.backend.Categories.repository;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubCategoryRepository extends CrudRepository<SubCategory, Long> {

    List<SubCategory> findByName(String subCategoryName);

    List<SubCategory> findByCategoryCategoryID(Long categoryID);

}
