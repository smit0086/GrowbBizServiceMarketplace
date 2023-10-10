package com.growbiz.backend.Categories.repository;

import com.growbiz.backend.Categories.models.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubCategoryRepository extends CrudRepository<SubCategory, Long> {
}
