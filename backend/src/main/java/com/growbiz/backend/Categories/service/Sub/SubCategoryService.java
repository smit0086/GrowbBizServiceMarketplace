package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;

import com.growbiz.backend.Services.models.Services;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    ISubCategoryRepository iSubCategoryRepository;

    @Override
    public SubCategory getSubCategoryByID(Long categoryID) {
        try {
            SubCategory subCategory = iSubCategoryRepository.findById(categoryID).get();
            return subCategory;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SubCategory> fetchSubCategoryList() {
        try {
            return (List<SubCategory>) iSubCategoryRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public SubCategory addCategory(SubCategory newSubCategory, Long newCategoryID) {
        try {
            return iSubCategoryRepository.save(newSubCategory);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategory category, Long categoryID) {
        try {
            SubCategory categoryUpdated = iSubCategoryRepository.findById(categoryID).get();

            if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
                categoryUpdated.setName(category.getName());
            }

            if (Objects.nonNull(category.getSuperCategoryID())) {
                categoryUpdated.setSuperCategoryID(category.getSuperCategoryID());
            }

            return iSubCategoryRepository.save(categoryUpdated);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteCategory(Long categoryID) {
        try {
            iSubCategoryRepository.deleteById(categoryID);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
