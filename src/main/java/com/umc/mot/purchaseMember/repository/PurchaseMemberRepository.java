package com.umc.mot.purchaseMember.repository;

import com.umc.mot.purchaseMember.entity.PurchaseMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseMemberRepository extends JpaRepository<PurchaseMember, Integer> {
    Optional<PurchaseMember> findByEmail(String email);
}
