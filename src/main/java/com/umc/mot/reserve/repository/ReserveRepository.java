package com.umc.mot.reserve.repository;

import com.umc.mot.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
}
