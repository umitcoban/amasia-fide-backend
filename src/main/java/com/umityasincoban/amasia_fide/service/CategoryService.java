package com.umityasincoban.amasia_fide.service;

import com.umityasincoban.amasia_fide.dto.CategoryDTO;
import com.umityasincoban.amasia_fide.entity.Category;
import com.umityasincoban.amasia_fide.mapper.CategoryMapper;
import com.umityasincoban.amasia_fide.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public Category createNewCategory(Category category){
        return categoryRepository.saveAndFlush(category);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }

    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDTO getCategoryDTOById(int id) {
        return categoryMapper.toCategoryDTO(getCategoryById(id));
    }

    public List<CategoryDTO> getAllCategoriesDTO() {
        return categoryMapper.toCategoryDTOs(getAllCategories());
    }

    public CategoryDTO createAndGetCategoryDTO(CategoryDTO categoryDTO) {
        return categoryMapper.toCategoryDTO(createNewCategory(categoryMapper.toCategory(categoryDTO)));
    }

    public CategoryDTO updateAndGetCategoryDTO(CategoryDTO categoryDTO) {
        return categoryMapper.toCategoryDTO(updateCategory(categoryMapper.toCategory(categoryDTO)));
    }
}
