package com.growbiz.backend.Categories.service.Super;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
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
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository iCategoryRepository;

    @Override
    public Category getCategoryByID(Long categoryID) {
        Optional<Category> category = iCategoryRepository.findById(categoryID);

        if (category.isPresent()) {
            return category.get();
        } else {
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
    public Category addCategory(Category newCategory) {
        List<Category> category = iCategoryRepository.findByName(newCategory.getName());
        if (category.isEmpty()) {
            return iCategoryRepository.save(newCategory);
        }
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> categoryToUpdate = iCategoryRepository.findById(category.getCategoryID());

        if (categoryToUpdate.isPresent()) {
            if (Objects.nonNull(category.getName()) && !"".equalsIgnoreCase(category.getName())) {
                if (Objects.nonNull(category.getTax()) && !"".equalsIgnoreCase(category.getTax())) {
                    return iCategoryRepository.save(category);
                }
            }
        }
        return null;
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
