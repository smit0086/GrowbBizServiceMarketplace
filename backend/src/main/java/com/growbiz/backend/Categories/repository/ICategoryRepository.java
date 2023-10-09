package com.growbiz.backend.Categories.repository;

import com.growbiz.backend.Categories.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<Category, Long> {

    public Category findCategory(Long id);
    public Category addCategory(Category category);
    public Category updateCategory(Long id, Category category);
    public Category deleteCategory(Long id);
}
