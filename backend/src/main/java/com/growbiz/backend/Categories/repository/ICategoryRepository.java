package com.growbiz.backend.Categories.repository;

import com.growbiz.backend.Categories.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICategoryRepository extends CrudRepository<Category, Long> {

}
