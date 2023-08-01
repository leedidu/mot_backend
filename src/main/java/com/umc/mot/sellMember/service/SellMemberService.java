package com.umc.mot.sellMember.service;

import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.repository.SellMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SellMemberService {

    private final SellMemberRepository sellMemberRepository;

    //Create
    public SellMember createSellMember(SellMember sellmember) {

        return sellMemberRepository.save(sellmember);
    }

    // Read
    public SellMember findMember(int sellMemberId) {
        SellMember member = verifiedMember(sellMemberId);
        return member;
    }


    // Update
    public SellMember patchMember(SellMember member) {
        SellMember findMember = verifiedMember(member.getSellMemberId());
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
    public SellMember verifiedMember(int sellMemberId) {
        Optional<SellMember> member = sellMemberRepository.findById(sellMemberId);
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.SELL_MEMBER_NOT_FOUND));

    }
}