package com.growbiz.backend.Categories;

import com.growbiz.backend.Categories.models.Category;
import com.growbiz.backend.Categories.models.SubCategory;
import com.growbiz.backend.Categories.repository.ICategoryRepository;
import com.growbiz.backend.Categories.repository.ISubCategoryRepository;
import com.growbiz.backend.Categories.service.Sub.SubCategoryService;
import com.growbiz.backend.RequestResponse.SubCategory.SubCategoryRequest;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubCategoryServiceTests {
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ISubCategoryRepository subCategoryRepository;
    @Mock
    SubCategoryRequest mockSubCategoryRequest;
    @Mock
    Category mockCategory;
    @Mock
    SubCategory mockSubCategory;
    @Mock
    SubCategory mockSubCategoryToAdd;
    @Mock
    SubCategory mockSubCategoryUpdated;
    @Mock
    List<SubCategory> mockEmpty = new ArrayList<>();
    @InjectMocks
    private SubCategoryService subCategoryService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.mockSubCategory = SubCategory
                .builder()
                .subCategoryID(1L)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        this.mockSubCategoryToAdd = SubCategory
                .builder()
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .category(mockCategory)
                .build();
        this.mockCategory = Category
                .builder()
                .categoryID(1L)
                .name(TestConstants.TEST_CATEGORY_NAME)
                .tax(TestConstants.TEST_CATEGORY_TAX)
                .build();
        this.mockSubCategoryRequest = SubCategoryRequest
                .builder()
                .subCategoryID(1L)
                .name(TestConstants.TEST_SUBCATEGORY_NAME)
                .categoryID(1L)
                .isSubCategory(true)
                .build();
        this.mockSubCategoryUpdated = SubCategory
                .builder()
                .subCategoryID(1L)
                .name(TestConstants.TEST_SUBCATEGORY_NAME + "11")
                .category(mockCategory)
                .build();
    }

    @Test
    public void getSubCategoryByIDSuccessTest() {
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.ofNullable(mockSubCategory));
        SubCategory results = subCategoryService.getSubCategoryByID(1L);
        assertEquals(mockSubCategory, results);
    }

    @Test
    public void getNullSubCategoryIDTest() throws Exception {
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.empty());
        SubCategory results = subCategoryService.getSubCategoryByID(1L);
        assertNull(results);
    }

    @Test
    public void fetchSubCategoryListSuccessTest() {
        when(subCategoryRepository.findAll()).thenReturn(List.of(mockSubCategory));
        List<SubCategory> results = subCategoryService.fetchSubCategoryList();
        assertEquals(List.of(mockSubCategory), results);
    }

    @Test
    public void fetchNullSubCategoryListTest() {
        when(subCategoryRepository.findAll()).thenThrow(new NullPointerException("Test Exception"));
        List<SubCategory> results = subCategoryService.fetchSubCategoryList();
        assertNull(results);
    }

    @Test
    public void fetchSubCategoryListForCategoryIDSuccessTest() {
        when(subCategoryRepository.findByCategoryCategoryID(1L)).thenReturn(List.of(mockSubCategory));
        List<SubCategory> results = subCategoryService.fetchSubCategoryListForCategoryID(1L);
        assertEquals(List.of(mockSubCategory), results);
    }

    @Test
    public void fetchNullSubCategoryListForCategoryIDTest() {
        when(subCategoryRepository.findByCategoryCategoryID(1L)).thenThrow(new NullPointerException("Test Exception"));
        List<SubCategory> results = subCategoryService.fetchSubCategoryListForCategoryID(1L);
        assertNull(results);
    }

    @Test
    public void addSubCategorySuccessTest() {
        when(subCategoryRepository.findByName(mockSubCategoryRequest.getName())).thenReturn(mockEmpty);
        when(mockEmpty.isEmpty()).thenReturn(true);
        when(categoryRepository.findById(mockCategory.getCategoryID())).thenReturn(Optional.of(mockCategory));
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(mockSubCategory);

        subCategoryService.addSubCategory(mockSubCategoryRequest);

        assertNotNull(subCategoryRepository.findByName(TestConstants.TEST_SUBCATEGORY_NAME));
        verify(subCategoryRepository, times(1)).save(
                argThat(subCategory -> subCategory.getName().equals(mockSubCategoryRequest.getName())));
    }

    @Test
    public void addExistingSubCategorySuccessTest() {
        when(subCategoryRepository.findByName(mockSubCategoryRequest.getName())).thenReturn(List.of(mockSubCategory));
        SubCategory results = subCategoryService.addSubCategory(mockSubCategoryRequest);
        assertNull(results);
    }

    @Test
    public void updateExistingSubCategorySuccessTest() {
        when(subCategoryRepository.findById(mockSubCategoryRequest.getCategoryID())).thenReturn(Optional.ofNullable(mockSubCategory));
        when(categoryRepository.findById(1L)).thenReturn(Optional.ofNullable(mockCategory));
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(mockSubCategoryUpdated);

        subCategoryService.updateSubCategory(mockSubCategoryRequest, 1L);

        assertNotNull(subCategoryRepository.findByName(TestConstants.TEST_SUBCATEGORY_NAME));
        verify(subCategoryRepository, times(1)).save(
                argThat(subCategory -> subCategory.getName().equals(mockSubCategoryRequest.getName())));
    }

    @Test
    public void deleteExistingSubCategoryTest() {
        doNothing().when(subCategoryRepository).deleteById(mockSubCategory.getSubCategoryID());
        subCategoryService.deleteSubCategory(mockSubCategory.getSubCategoryID());
        verify(subCategoryRepository).deleteById(mockSubCategory.getSubCategoryID());
    }
}
