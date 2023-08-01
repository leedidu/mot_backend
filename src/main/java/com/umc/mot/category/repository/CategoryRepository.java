package com.umc.mot.category.repository;

import com.umc.mot.category.entity.Category;
import com.umc.mot.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
