package com.umc.mot.category.service;

import com.umc.mot.category.repository.CategoryRepository;
import com.umc.mot.category.entity.Category;
import com.umc.mot.category.repository.CategoryRepository;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //Create
    public Category createCategory(Category category) {

        return categoryRepository.save(category);
    }

    // Read
    public Category findCategory(int categoryId) {
        Category category = verifiedCategory(categoryId);
        return category;
    }

    // 모든 카테고리 조회
    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }


    // Update
    public Category patchCategory(Category category) {
        Category findCategory = verifiedCategory(category.getId());
        Optional.ofNullable(category.getId()).ifPresent(findCategory::setId);
        Optional.ofNullable(category.getName()).ifPresent(findCategory::setName);

        return categoryRepository.save(findCategory);
    }

    // Delete
    public void deleteCategory(int categoryId) {
        Category category = verifiedCategory(categoryId);
        categoryRepository.delete(category);
    }

    // 멤버 검증
    public Category verifiedCategory(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        return category.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CATEGORY_NOT_FOUND));

    }
}
