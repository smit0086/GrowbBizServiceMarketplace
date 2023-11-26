package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;
import com.growbiz.backend.RequestResponse.SubCategory.SubCategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<SubCategory> subCategory = iSubCategoryRepository.findById(subCategoryID);

        if (subCategory.isPresent()) {
            return subCategory.get();
        } else {
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
    public List<SubCategory> fetchSubCategoryListForCategoryID(Long categoryId) {
        try {
            return iSubCategoryRepository.findByCategoryCategoryID(categoryId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SubCategory addSubCategory(SubCategoryRequest newSubCategory) {
        List<SubCategory> subCategory = iSubCategoryRepository.findByName(newSubCategory.getName());
        if (subCategory.isEmpty()) {
            Category category = iCategoryRepository.findById(newSubCategory.getCategoryID()).get();
            SubCategory subCategoryToAdd = SubCategory.builder()
                    .name(newSubCategory.getName())
                    .category(category)
                    .build();
            return iSubCategoryRepository.save(subCategoryToAdd);
        }
        return null;
    }

    @Override
    public SubCategory updateSubCategory(SubCategoryRequest subCategoryRequest, Long categoryID) {
        Optional<SubCategory> subCategoryToUpdate = iSubCategoryRepository.findById(subCategoryRequest.getSubCategoryID());

        if (subCategoryToUpdate.isEmpty()) {
            return null;
        } else {
            Category category = iCategoryRepository.findById(categoryID).get();
            SubCategory subCategoryUpdated = SubCategory.builder()
                    .subCategoryID(subCategoryToUpdate.get().getSubCategoryID())
                    .name(subCategoryRequest.getName())
                    .category(category)
                    .build();
            return iSubCategoryRepository.save(subCategoryUpdated);
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
