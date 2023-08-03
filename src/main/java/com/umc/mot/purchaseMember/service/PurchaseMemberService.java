package com.umc.mot.purchaseMember.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.purchaseMember.repository.PurchaseMemberRepository;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PurchaseMemberService {

    private final PurchaseMemberRepository purchaseMemberRepository;

    //Create
    public PurchaseMember createPurchaseMember(PurchaseMember purchasemember) {

        return purchaseMemberRepository.save(purchasemember);
    }

    // Read
    public PurchaseMember findMember(int purchaseMemberId) {
        PurchaseMember member = verifiedMember(purchaseMemberId);
        return member;
    }


    // Update
    public PurchaseMember patchMember(PurchaseMember member) {
        PurchaseMember findMember = verifiedMember(member.getPurchaseMemberId());
        Optional.ofNullable(member.getId()).ifPresent(findMember::setId);
        Optional.ofNullable(member.getName()).ifPresent(findMember::setName);
        Optional.ofNullable(member.getImageUrl()).ifPresent(findMember::setImageUrl);
        Optional.ofNullable(member.getEmail()).ifPresent(findMember::setEmail);
        Optional.ofNullable(member.getPhone()).ifPresent(findMember::setPhone);
        Optional.ofNullable(member.getPw()).ifPresent(findMember::setPw);
        Optional.ofNullable(member.getHost()).ifPresent(findMember::setHost);
        Optional.ofNullable(member.getToken()).ifPresent(findMember::setToken);


        return purchaseMemberRepository.save(findMember);
    }

    // Delete
    public void deleteMember(int memberId) {
        PurchaseMember member = verifiedMember(memberId);
        purchaseMemberRepository.delete(member);
    }

    // 멤버 검증
    public PurchaseMember verifiedMember(int purchaseMemberId) {
        Optional<PurchaseMember> member = purchaseMemberRepository.findById(purchaseMemberId);
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SELL_MEMBER_NOT_FOUND));

    }
}