package com.growbiz.backend.Categories.service;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public Optional<Category> getCategoryByID(Long categoryID) {
        return iCategoryRepository.findById(categoryID);
    }

    @Override
    public List<Category> fetchCategoryList() {
        return (List<Category>) iCategoryRepository.findAll();
    }

    //needs checking
    @Override
    public void addCategory(Category newCategory, Long newCategoryID) {
        iCategoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategory(Category category, Long categoryID) {
        Category categoryUpdated = iCategoryRepository.findById(categoryID).get();

        if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
            categoryUpdated.setName(category.getName());
        }

        if (Objects.nonNull(category.getTax()) && !"".equalsIgnoreCase(category.getTax())) {
            categoryUpdated.setTax(category.getTax());
        }

        return iCategoryRepository.save(categoryUpdated);
    }

    @Override
    public void deleteCategory(Long categoryID) {
        iCategoryRepository.deleteById(categoryID);
    }
}
