package com.umc.mot.token.service;

import com.umc.mot.event.UserRegistrationApplicationEvent;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.oauth2.utils.CustomAuthorityUtils;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.service.SellMemberService;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final PurchaseMemberService purchaseMemberService;
    private final SellMemberService sellMemberService;
    private final CustomAuthorityUtils authorityUtils;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;

    // 회원가입(아이디, 비밀번호)
    public Token createToken(Token token, String phone) {
        // 구매자 회원 생성
        PurchaseMember purchaseMember = purchaseMemberService.createPurchaseMember(token, phone);

        // 토큰 생성
        token.setRoles(authorityUtils.createRoles("what@email.com")); // 권한 설정
        token.setLoginId(token.getLoginId());
        token.setLoginPw(passwordEncoder.encode(token.getLoginPw())); // 비밀번호 인코딩
        token.setPurchaseMember(purchaseMember);
        token = tokenRepository.save(token);

        publisher.publishEvent(new UserRegistrationApplicationEvent(token));
        return token;
    }

    // 회원가입(oauth - google)
    public Token createToken(PurchaseMember purchaseMember) {
        Token token = new Token();
        token.setRoles(authorityUtils.createRoles(purchaseMember.getEmail())); // 권한 설정
        token.setPurchaseMember(purchaseMember);
        token = tokenRepository.save(token);

        publisher.publishEvent(new UserRegistrationApplicationEvent(token));
        return token;
    }

    public Token test() {
        return getLoginToken();
    }

    // 로그인한 회원의 토큰 가져오기
    public Token getLoginToken() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //SecurityContextHolder에서 회원정보 가져오기
        Optional<Token> token;

        if(principal.toString().contains("@")) {
            PurchaseMember purchaseMember = purchaseMemberService.verifiedByEmail(principal.toString());
            if(purchaseMember != null) {
                token = Optional.ofNullable(purchaseMember.getToken());
            } else {
                SellMember sellMember = sellMemberService.verifiedByEmail(principal.toString());
                token = Optional.ofNullable(sellMember.getToken());
            }
        }
        else token = tokenRepository.findByLoginId(principal.toString());


        System.out.println("!! principal : " + principal.toString());

        return token.orElse(null);
    }

    // 로그인한 회원의 purchaseMember 가져오기
    public PurchaseMember getLoginPurchaseMember() {
        Token token = getLoginToken();

        PurchaseMember member = token.getPurchaseMember();
        if(member == null) {
            throw new BusinessLogicException(ExceptionCode.PURCHASE_MEMBER_NOT_FOUND);
        }

        return member;
    }

    // 로그인한 회원의 sellMember 가져오기
    public SellMember getLoginSellMember() {
        Token token = getLoginToken();

        SellMember member = token.getSellMember();
        if(member == null) {
            throw new BusinessLogicException(ExceptionCode.SELL_MEMBER_NOT_FOUND);
        }

        return member;
    }

    // Read
    public Token findTokenId(int tokenId){
        Token token = verifiedTokenId(tokenId);
        return token;
    }


    // Update
    public Token patchToken(Token token) {
        Token findToken = verifiedTokenId(token.getId());
        Optional.ofNullable(token.getAccessToken()).ifPresent(findToken::setAccessToken);
        Optional.ofNullable(token.getRefreshToken()).ifPresent(findToken::setRefreshToken);
        Optional.ofNullable(token.getLoginId()).ifPresent(findToken::setLoginId);
        Optional.ofNullable(token.getLoginPw()).ifPresent(findToken::setLoginPw);

        return tokenRepository.save(findToken);
    }

    // Delete
    public void deleteToken(int tokenId) {
        Token token = verifiedTokenId(tokenId);
        tokenRepository.delete(token);
    }

    // 토큰 검증
    public Token verifiedTokenId(int tokenId) {
        Optional<Token> token = tokenRepository.findById(tokenId);
        return token.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_MEMBER_NOT_FOUND));
    }

    // 로그인 아이디 검증
    public Token verifiedLoginId(String loginId) {
        Optional<Token> token = tokenRepository.findByLoginId(loginId);
        return token.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TOKEN_MEMBER_NOT_FOUND));
    }

    // 아이디 중복 확인
    public boolean checkLoginId(String loginId) {
        Optional<Token> token = tokenRepository.findByLoginId(loginId);
        System.out.println("!! token id : "  + token.orElse(new Token()).getId());
        if(token.orElse(new Token()).getId() == 0) return true; // 아이디가 존재하지 않으므로 아이디 사용 가능
        else return false; // 아이디가 존재함으로 아이디 사용 불가능
    }
}
