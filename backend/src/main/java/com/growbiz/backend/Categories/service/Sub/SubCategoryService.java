package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.Category;
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
        try {
            return iSubCategoryService.findById(categoryID);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SubCategory> fetchSubCategoryList() {
        try {
            return (List<SubCategory>) iSubCategoryService.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void addCategory(SubCategory newSubCategory, Long newCategoryID) {
        try {
            iSubCategoryService.save(newSubCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategory category, Long categoryID) {
        try {
            SubCategory categoryUpdated = iSubCategoryService.findById(categoryID).get();

            if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
                categoryUpdated.setName(category.getName());
            }

            if (Objects.nonNull(category.getSuperCategoryID())) {
                categoryUpdated.setSuperCategoryID(category.getSuperCategoryID());
            }

            return iSubCategoryService.save(categoryUpdated);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteCategory(Long categoryID) {
        try {
            iSubCategoryService.deleteById(categoryID);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
