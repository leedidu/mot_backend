package com.umc.mot.hotelCategory.repository;

import com.umc.mot.hotelCategory.entity.HotelCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelCategoryRepository extends JpaRepository<HotelCategoryEntity, Integer> {
}
