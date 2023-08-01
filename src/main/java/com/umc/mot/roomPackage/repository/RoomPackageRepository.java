package com.umc.mot.roomPackage.repository;

import com.umc.mot.roomPackage.entity.RoomPackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomPackageRepository extends JpaRepository<RoomPackageEntity, Integer> {
}
