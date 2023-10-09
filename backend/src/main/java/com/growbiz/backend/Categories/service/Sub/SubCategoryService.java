package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SubCategoryService implements ISubCategoryService {

    @Autowired
    ISubCategoryRepository iSubCategoryService;

    @Override
    public Optional<SubCategory> getSubCategoryByID(Long categoryID) {
        return iSubCategoryService.findById(categoryID);
    }

    @Override
    public List<SubCategory> fetchSubCategoryList() {
        return (List<SubCategory>) iSubCategoryService.findAll();
    }

    //needs checking
    @Override
    public void addCategory(SubCategory newSubCategory, Long newCategoryID) {
        iSubCategoryService.save(newSubCategory);
    }

    @Override
    public SubCategory updateSubCategory(SubCategory category, Long categoryID) {
        SubCategory categoryUpdated = iSubCategoryService.findById(categoryID).get();

        if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
            categoryUpdated.setName(category.getName());
        }

        if (Objects.nonNull(category.getSuperCategoryID())) {
            categoryUpdated.setSuperCategoryID(category.getSuperCategoryID());
        }

        return iSubCategoryService.save(categoryUpdated);
    }

    @Override
    public void deleteCategory(Long categoryID) {
        iSubCategoryService.deleteById(categoryID);
    }
}
