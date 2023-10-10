package com.growbiz.backend.Categories.service.Sub;

import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryService implements ISubCategoryService {

    @Autowired
    ISubCategoryRepository iSubCategoryRepository;

    @Override
    public Optional<SubCategory> getSubCategoryByID(Long categoryID) {
        try {
            return iSubCategoryRepository.findById(categoryID);
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
    public void addCategory(SubCategory newSubCategory, Long newCategoryID) {
        try {
            iSubCategoryRepository.save(newSubCategory);
        } catch (Exception e) {
            throw e;
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
