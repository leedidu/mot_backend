package com.umc.mot.packagee.repository;

import com.umc.mot.packagee.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Integer> {
}
