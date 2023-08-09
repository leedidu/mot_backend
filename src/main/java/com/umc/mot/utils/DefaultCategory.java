package com.umc.mot.utils;

import com.umc.mot.category.entity.Category;
import com.umc.mot.category.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DefaultCategory {
    private final CategoryRepository categoryRepository;

    // 기본 필터
    List<String> categories = new ArrayList<>(List.of(
            "조식 제공",
            "오션뷰",
            "애견동반",
            "풀빌라",
            "사진 맛집",
            "뷰 맛집",
            "캠핑&카라반",
            "키즈 전용",
            "모텔",
            "호텔",
            "게스트 하우스"
    ));

    // 그룹 필터
    List<String> groupCategories = new ArrayList<>(List.of(
            "단체 바베큐",
            "넓은 강당",
            "캠프 파이어"
    ));



    DefaultCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

        // 기본 필터
        for(String categoryName : categories) {
            Category category = new Category();
            category.setName(categoryName);
            category.setType(Category.Type.FILTER);
            categoryRepository.save(category);
        }

        // 그룹 필터
        for(String categoryName : groupCategories) {
            Category category = new Category();
            category.setName(categoryName);
            category.setType(Category.Type.GROUP);
            categoryRepository.save(category);
        }
    }
}
