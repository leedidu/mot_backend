package com.umc.mot.roomPackage.repository;

import com.umc.mot.roomPackage.entity.RoomPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomPackageRepository extends JpaRepository<RoomPackage, Integer> {

    @Query("select roomPack.room.id from RoomPackage roomPack where roomPack.packages.id =:packageId")
    List<Integer> findRoomByPackage(int packageId);
}
