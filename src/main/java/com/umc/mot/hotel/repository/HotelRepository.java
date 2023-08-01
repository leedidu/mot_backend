package com.umc.mot.hotel.repository;

import com.umc.mot.hotel.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity, Integer> {
}
