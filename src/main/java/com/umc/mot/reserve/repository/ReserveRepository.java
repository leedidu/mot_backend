package com.umc.mot.reserve.repository;

import com.umc.mot.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    List<Reserve> findAll(); // 모든 예약 정보 가져오기
}
