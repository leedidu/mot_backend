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
    private final SellMemberRepository sellMemberRepository;
    TokenService tokenService;

    //Create
    public PurchaseMember createPurchaseMember(PurchaseMember purchasemember) {

        return purchaseMemberRepository.save(purchasemember);
    }

    public PurchaseMember createPurchaseMember(Token token, String phone) {
        PurchaseMember purchaseMember = new PurchaseMember();
        purchaseMember.setName("nickname" + (token.getId()+1));
        purchaseMember.setPhone(phone);
//        purchaseMember.setToken(token);

        return purchaseMemberRepository.save(purchaseMember);
    }

    // Read
    public PurchaseMember findMember(int purchaseMemberId) {
        PurchaseMember member = verifiedMember(purchaseMemberId);
        return member;
    }


    // Update
    public PurchaseMember patchMember(PurchaseMember member) {
        PurchaseMember findMember = verifiedMember(member.getPurchaseMemberId());
        Optional.ofNullable(member.getName()).ifPresent(findMember::setName);
        Optional.ofNullable(member.getImageUrl()).ifPresent(findMember::setImageUrl);
        Optional.ofNullable(member.getEmail()).ifPresent(findMember::setEmail);
        Optional.ofNullable(member.getPhone()).ifPresent(findMember::setPhone);
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
        return member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PURCHASE_MEMBER_NOT_FOUND));

    }

    // 이메일을 통한 멤버 검증
    public PurchaseMember verifiedByEmail(String email) {
        Optional<PurchaseMember> member = purchaseMemberRepository.findByEmail(email);
        return member.orElse(null);

    }


    public SellMember PurchaseMemberTosellMember(String email) {
        PurchaseMember member1 = verifiedByEmail(email);//이메일로 멤버 검증
        SellMember sellMember = new SellMember();
        Optional.ofNullable(member1.getName()).ifPresent(sellMember::setName);
        Optional.ofNullable(member1.getImageUrl()).ifPresent(sellMember::setImageUrl);
        Optional.ofNullable(member1.getEmail()).ifPresent(sellMember::setEmail);
        Optional.ofNullable(member1.getPhone()).ifPresent(sellMember::setPhone);
        Optional.ofNullable(member1.getEmail()).ifPresent(sellMember::setEmail);
        Optional.ofNullable(member1.getHost()).ifPresent(sellMember::setHost);
        Optional.ofNullable(member1.getToken()).ifPresent(sellMember::setToken);

        purchaseMemberRepository.delete(member1);
        return sellMemberRepository.save(sellMember);

    }


}