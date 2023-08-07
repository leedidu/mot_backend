package com.umc.mot.sellMember.repository;

import com.umc.mot.sellMember.entity.SellMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellMemberRepository extends JpaRepository<SellMember, Integer> {
    Optional<SellMember> findByEmail(String email);
    Optional<SellMember> findByPhone(String phone);
}