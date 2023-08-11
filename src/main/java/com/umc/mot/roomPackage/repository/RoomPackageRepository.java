package com.umc.mot.roomPackage.repository;

import com.umc.mot.roomPackage.entity.RoomPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface RoomPackageRepository extends JpaRepository<RoomPackage, Integer> {

    @Query("select roomPack.room.id from RoomPackage roomPack where roomPack.packages.id =:packageId")
    List<Integer> findRoomByPackage(int packageId);

    @Modifying
    @Transactional
    @Query("delete from RoomPackage roompack where roompack.packages.id=:packageId")
    void deleteRoomPackageByPackage(int packageId);
}
