package com.umc.mot.reserve.repository;

import com.umc.mot.reserve.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

    @Query("select reserve.roomsId from Reserve reserve where reserve.id=:reserveId")
    Optional<Integer> findRoomByReserveId(int reserveId);

    @Query("select reserve.packagesId from Reserve reserve where reserve.id=:reserveId")
    Optional<Integer> findPackageByReserveId(int reserveId);

    @Query("select r from Reserve r where r.purchaseMember.purchaseMemberId=:purchaseMemberId")
    List<Reserve> findReserveByPurchaseMember(int purchaseMemberId);
}
