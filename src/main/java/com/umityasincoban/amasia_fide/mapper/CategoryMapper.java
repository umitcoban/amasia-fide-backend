package com.umityasincoban.amasia_fide.mapper;

import com.umityasincoban.amasia_fide.dto.CategoryDTO;
import com.umityasincoban.amasia_fide.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "categoryId", target = "id")
    CategoryDTO toCategoryDTO(Category category);

    @Mapping(source = "categoryId", target = "id")
    List<CategoryDTO> toCategoryDTOs(List<Category> categories);

    @Mapping(source = "id", target = "categoryId")
    Category toCategory(CategoryDTO categoryDTO);


}
