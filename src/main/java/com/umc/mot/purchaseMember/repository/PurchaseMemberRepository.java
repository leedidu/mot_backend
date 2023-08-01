package com.umc.mot.purchaseMember.repository;

import com.umc.mot.purchaseMember.entity.PurchaseMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseMemberRepository extends JpaRepository<PurchaseMemberEntity, Integer> {
}
