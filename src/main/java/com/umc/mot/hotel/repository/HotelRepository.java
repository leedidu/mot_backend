package com.umc.mot.hotel.repository;

import com.umc.mot.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("SELECT h FROM Hotel h WHERE (h.name LIKE %:post% or h.address Like %:post%) and (h.minPeople<=:people and h.maxPeople>=:people)")
    ArrayList<Hotel> findByName(String post,int people);
}
