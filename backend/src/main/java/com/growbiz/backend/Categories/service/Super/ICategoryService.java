package com.growbiz.backend.Categories.service.Super;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.User.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICategoryService {

    Optional<Category> getCategoryByID(Long categoryID);

    List<Category> fetchCategoryList();

    void addCategory(Category newCategory, Long newCategory_ID);

    Category updateCategory(Category category, Long categoryID);

    void deleteCategory(Long categoryID);
}
