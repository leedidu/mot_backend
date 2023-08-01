package com.umc.mot.sellMember.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.sellMember.entity.SellMemberEntity;
import com.umc.mot.sellMember.repository.SellMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SellMemberService {

    private final SellMemberRepository sellMemberRepository;

    //Create
    public SellMemberEntity createSellMember(SellMemberEntity sellmember) {

        return sellMemberRepository.save(sellmember);
    }

    // Read
    public SellMemberEntity findMember(int sellMemberId) {
        SellMemberEntity member = verifiedMember(sellMemberId);
        return member;
    }


    // Update
    public SellMemberEntity patchMember(SellMemberEntity member) {
        SellMemberEntity findMember = verifiedMember(member.getSellMemberId());
        Optional.ofNullable(member.getId()).ifPresent(findMember::setId);
        Optional.ofNullable(member.getName()).ifPresent(findMember::setName);
        Optional.ofNullable(member.get)


        return memberRepository.save(findMember);
    }

    // Delete
    public void deleteMember(long memberId) {
        MemberEntity member = verifiedMember(memberId);
        memberRepository.delete(member);
    }

    // 멤버 검증
    public SellMemberEntity verifiedMember(int sellMemberId) {
        Optional<SellMemberEntity> member = sellMemberRepository.findById(sellMemberId);
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SELL_MEMBER_NOT_FOUND));

    }
}