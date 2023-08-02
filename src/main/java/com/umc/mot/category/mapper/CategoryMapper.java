package com.umc.mot.category.mapper;


import com.umc.mot.category.dto.CategoryRequestDto;
import com.umc.mot.category.dto.CategoryResponseDto;
import com.umc.mot.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto.Response CategoryToCategoryResponseDto(Category category);
    Category CategoryRequestDtoPostToCategory(CategoryRequestDto.Post post);
    Category CategoryRequestDtoPatchToCategory(CategoryRequestDto.Patch patch);
}
