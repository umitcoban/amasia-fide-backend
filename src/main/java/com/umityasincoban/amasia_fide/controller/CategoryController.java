package com.umityasincoban.amasia_fide.controller;

import com.umityasincoban.amasia_fide.dto.CategoryDTO;
import com.umityasincoban.amasia_fide.entity.ApiResponse;
import com.umityasincoban.amasia_fide.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryDTO> getCategoryById(@PathVariable int id) {
        return new ApiResponse<>(HttpStatus.OK.value(), categoryService.getCategoryDTOById(id), System.currentTimeMillis());
    }

    @GetMapping
    public ApiResponse<List<CategoryDTO>> getAllCategories() {
        return new ApiResponse<>(HttpStatus.OK.value(), categoryService.getAllCategoriesDTO(), System.currentTimeMillis());
    }

    @PostMapping
    public ApiResponse<CategoryDTO> createNewCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return new ApiResponse<>(HttpStatus.OK.value(), categoryService.createAndGetCategoryDTO(categoryDTO), System.currentTimeMillis());
    }

    @PutMapping
    public ApiResponse<CategoryDTO> updateCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        return new ApiResponse<>(HttpStatus.OK.value(), categoryService.updateAndGetCategoryDTO(categoryDTO), System.currentTimeMillis());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "Deleted", System.currentTimeMillis());
    }

}
