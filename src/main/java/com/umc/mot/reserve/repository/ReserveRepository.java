package com.umc.mot.reserve.repository;

import com.umc.mot.reserve.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Integer> {
}
