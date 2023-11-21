package com.growbiz.backend.Categories;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import com.growbiz.backend.Categories.service.Super.CategoryService;
import com.growbiz.backend.TestConstants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    Category mockCategory;
    @Mock
    Category mockCategoryToAdd;
    @Mock
    Category mockCategoryUpdated;
    @Mock
    List<Category> mockEmpty = new ArrayList<>();
    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockCategory = Category
                .builder()
                .categoryID(1L)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        this.mockCategoryToAdd = Category
                .builder()
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        this.mockCategoryUpdated = Category
                .builder()
                .categoryID(1L)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
    }

    @Test
    public void getCategoryByIDSuccessTest() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(mockCategory));
        Category results = categoryService.getCategoryByID(1L);
        assertEquals(mockCategory, results);
    }

    @Test
    public void getNullCategoryIDTest() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        Category results = categoryService.getCategoryByID(1L);
        assertNull(results);
    }

    @Test
    public void fetchCategoryListSuccessTest() {
        when(categoryRepository.findAll()).thenReturn(List.of(mockCategory));
        List<Category> results = categoryService.fetchCategoryList();
        assertEquals(List.of(mockCategory), results);
    }

    @Test
    public void fetchNullCategoryListTest() {
        when(categoryRepository.findAll()).thenThrow(new NullPointerException("Test Exception"));
        List<Category> results = categoryService.fetchCategoryList();
        assertNull(results);
    }

    @Test
    public void addCategorySuccessTest() {
        when(categoryRepository.findByName(mockCategoryToAdd.getName())).thenReturn(mockEmpty);
        when(mockEmpty.isEmpty()).thenReturn(true);

        categoryService.addCategory(mockCategoryToAdd);

        assertNotNull(categoryRepository.findByName(TestConstants.TEST_CATEGORY_NAME));
        verify(categoryRepository, times(1)).save(
                argThat(Category -> Category.getName().equals(mockCategoryToAdd.getName())));
    }

    @Test
    public void addExistingCategorySuccessTest() {
        when(categoryRepository.findByName(mockCategoryToAdd.getName())).thenReturn(List.of(mockCategory));
        Category results = categoryService.addCategory(mockCategoryToAdd);
        assertNull(results);
    }

    @Test
    public void updateExistingCategorySuccessTest() {
        when(categoryRepository.findById(mockCategoryUpdated.getCategoryID())).thenReturn(Optional.ofNullable(mockCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategoryUpdated);

        categoryService.updateCategory(mockCategoryUpdated);

        assertNotNull(categoryRepository.findByName(TestConstants.TEST_CATEGORY_NAME));
        verify(categoryRepository, times(1)).save(
                argThat(Category -> Category.getName().equals(mockCategoryUpdated.getName())));
    }

    @Test
    public void updateNonExistingCategorySuccessTest() {
        when(categoryRepository.findById(mockCategoryUpdated.getCategoryID())).thenReturn(Optional.ofNullable(null));
        Category results = categoryService.updateCategory(mockCategoryUpdated);

        assertNull(results);
        verify(categoryRepository, times(0)).save(
                argThat(Category -> Category.getName().equals(mockCategoryUpdated.getName())));
    }

    @Test
    public void deleteExistingCategoryTest() {
        doNothing().when(categoryRepository).deleteById(mockCategory.getCategoryID());
        categoryService.deleteCategory(mockCategory.getCategoryID());
        verify(categoryRepository).deleteById(mockCategory.getCategoryID());
    }
}
