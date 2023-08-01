package com.umc.mot.heart.repository;

import com.umc.mot.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeartRepository extends JpaRepository<Heart, Integer> {
}
