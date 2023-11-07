package com.growbiz.backend.Categories.service.Super;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.User.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ICategoryService {

    Category getCategoryByID(Long categoryID);

    List<Category> fetchCategoryList();

    Category addCategory(Category newCategory);

    Category updateCategory(Category category);

    void deleteCategory(Long categoryID);
}
