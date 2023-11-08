package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.models.SubCategoryRequest;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;
import com.growbiz.backend.Exception.exceptions.SubCategoryAlreadyExistsException;

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
    ICategoryRepository iCategoryRepository;
    @Autowired
    ISubCategoryRepository iSubCategoryRepository;

    @Override
    public SubCategory getSubCategoryByID(Long subCategoryID) {
        try {
            SubCategory subCategory = iSubCategoryRepository.findById(subCategoryID).get();
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
    public SubCategory addSubCategory(SubCategoryRequest newSubCategory) {
        try {
            boolean checkSubCategoryExists = Objects.nonNull(iSubCategoryRepository.findByName(newSubCategory.getSubCategoryName()));

            if(checkSubCategoryExists) {
                Category category = iCategoryRepository.findById(newSubCategory.getCategoryID()).get();
                SubCategory subCategoryToAdd =  SubCategory.builder()
                        .name(newSubCategory.getSubCategoryName())
                        .category(category)
                        .build();
                return iSubCategoryRepository.save(subCategoryToAdd);
            } else {
                throw new SubCategoryAlreadyExistsException("SubCategory already Exists");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategoryRequest subCategory, Long categoryID) {
        try {
            SubCategory updatedSubCategory = iSubCategoryRepository.findById(subCategory.getSubCategoryID()).get();

            if (Objects.nonNull(subCategory.getSubCategoryName()) && !"".equalsIgnoreCase(subCategory.getSubCategoryName())) {
                updatedSubCategory.setName(subCategory.getSubCategoryName());
            }

            if (Objects.nonNull(subCategory.getCategoryID())) {
                Category category = iCategoryRepository.findById(categoryID).get();
                updatedSubCategory.setCategory(category);
            }
            return iSubCategoryRepository.save(updatedSubCategory);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteSubCategory(Long subCategoryID) {
        try {
            iSubCategoryRepository.deleteById(subCategoryID);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
