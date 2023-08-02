package com.umc.mot.purchaseMember.repository;

import com.umc.mot.purchaseMember.entity.PurchaseMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMemberRepository extends JpaRepository<PurchaseMember, Integer> {
}
