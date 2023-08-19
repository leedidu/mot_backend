package com.umc.mot.heart.repository;

import com.umc.mot.heart.entity.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Integer> {

    @Query("select h from Heart h where h.purchaseMember.purchaseMemberId=:MemberId")
    List<Heart> findHeartByPurchaseMember(int MemberId);
}
