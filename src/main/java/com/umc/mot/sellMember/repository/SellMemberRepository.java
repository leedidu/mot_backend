package com.umc.mot.sellMember.repository;

import com.umc.mot.sellMember.entity.SellMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellMemberRepository extends JpaRepository<SellMemberEntity, Integer> {
}
