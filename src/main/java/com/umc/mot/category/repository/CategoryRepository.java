package com.umc.mot.category.repository;

import com.umc.mot.heart.entity.HeartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<HeartEntity, Integer> {
}
