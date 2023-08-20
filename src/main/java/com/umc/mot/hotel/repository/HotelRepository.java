package com.umc.mot.hotel.repository;

import com.umc.mot.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Query("SELECT h FROM Hotel h  WHERE (h.name LIKE %:post% or h.address Like %:post%)")
    ArrayList<Hotel> findByName(String post);

    @Query("SELECT h FROM Hotel h left join Reserve r on h.id=r.hotel.id WHERE (h.name LIKE %:post% or h.address Like %:post%) and (h.minPeople<=:people and h.maxPeople>=:people) and (r.checkIn>=:checkout or r.checkOut<=:checkin or r.checkIn=null or r.checkOut=null)")
        // @Query("SELECT h FROM Hotel h join Reserve r on h.id=r.hotel.id WHERE (h.name LIKE %:post% or h.address Like %:post%) and (h.minPeople<=:people and h.maxPeople>=:people)")
    ArrayList<Hotel> findByPeopleDay(String post, LocalDate checkin, LocalDate checkout, int people);




}
