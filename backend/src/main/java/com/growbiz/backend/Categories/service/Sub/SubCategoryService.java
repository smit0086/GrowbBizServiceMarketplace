package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
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
    public SubCategory addSubCategory(SubCategory newSubCategory) {
        try {
            if(Objects.nonNull(iSubCategoryRepository.findByName(newSubCategory.getName()))) {
                return iSubCategoryRepository.save(newSubCategory);
            } else {
                throw new SubCategoryAlreadyExistsException("SubCategory already Exists");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory updateSubCategory(SubCategory subCategory, Long categoryID) {
        try {
            SubCategory updatedSubCategory = iSubCategoryRepository.findById(subCategory.getSubCategoryID()).get();

            Category category = iSubCategoryRepository.findByCategoryCategoryID(categoryID);

            if (Objects.nonNull(subCategory.getName()) && !"".equalsIgnoreCase(subCategory.getName())) {
                updatedSubCategory.setName(subCategory.getName());
            }

            if (Objects.nonNull(subCategory.getCategory().getCategoryID())) {
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
