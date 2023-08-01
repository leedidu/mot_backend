package com.umc.mot.hotelCategory.repository;

import com.umc.mot.hotelCategory.entity.HotelCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelCategoryRepository extends JpaRepository<HotelCategory, Integer> {
}
