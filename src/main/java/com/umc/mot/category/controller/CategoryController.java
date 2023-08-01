package com.umc.mot.category.controller;

import com.umc.mot.category.mapper.CategoryMapper;
import com.umc.mot.category.service.CategoryService;
import com.umc.mot.category.dto.CategoryRequestDto;
import com.umc.mot.category.dto.CategoryResponseDto;
import com.umc.mot.category.entity.Category;
import com.umc.mot.category.mapper.CategoryMapper;
import com.umc.mot.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/category")
@Validated
@AllArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    // Create
    @PostMapping
    public ResponseEntity postCategory(@Valid @RequestBody CategoryRequestDto.Post post){
        Category category = categoryService.createCategory(categoryMapper.CategoryRequestDtoPostToCategory(post));
        CategoryResponseDto.Response response=categoryMapper.CategoryToCategoryResponseDto(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // Read
    @GetMapping
    public ResponseEntity getCategory(@Positive @RequestParam int categoryId){
        Category category = categoryService.findCategory(categoryId);
        CategoryResponseDto.Response response = categoryMapper.CategoryToCategoryResponseDto(category);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    // Update
    @PatchMapping("/{category-id}")
    public ResponseEntity patchCategory(@Positive @PathVariable("category-id") int categoryId,
                                     @RequestBody CategoryRequestDto.Patch patch) {
        patch.setId(categoryId);
        Category category = categoryService.patchCategory(categoryMapper.CategoryRequestDtoPatchToCategory(patch));
        CategoryResponseDto.Response response =categoryMapper.CategoryToCategoryResponseDto(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{category-id}")
    public ResponseEntity deleteCategory(@Positive @PathVariable("category-id") int categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
