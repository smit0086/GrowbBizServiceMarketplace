package com.growbiz.backend.Categories.service.Super;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public Category getCategoryByID(Long categoryID) {
        try {
            return iCategoryRepository.findById((categoryID)).get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Category> fetchCategoryList() {
        try {
            return (List<Category>) iCategoryRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Category addCategory(Category newCategory, Long newCategoryID) {
        try {
            return iCategoryRepository.save(newCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Category updateCategory(Category category, Long categoryID) {
        try {
            Category categoryUpdated = iCategoryRepository.findById(categoryID).get();

            if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
                categoryUpdated.setName(category.getName());
            }

            if (Objects.nonNull(category.getTax()) && !"".equalsIgnoreCase(category.getTax())) {
                categoryUpdated.setTax(category.getTax());
            }
            return iCategoryRepository.save(categoryUpdated);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteCategory(Long categoryID) {
        try {
            iCategoryRepository.deleteById(categoryID);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
