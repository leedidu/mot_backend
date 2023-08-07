package com.umc.mot.oauth2.filter;

import com.umc.mot.oauth2.utils.CustomAuthorityUtils;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.sellMember.service.SellMemberService;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.service.TokenService;
import com.umc.mot.utils.CustomCookie;
import com.umc.mot.exception.BusinessLogicException;
import com.umc.mot.exception.ExceptionCode;
import com.umc.mot.oauth2.jwt.JwtTokenizer;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final TokenService tokenService;
    private final SellMemberService sellMemberService;
    private final PurchaseMemberService purchaseMemberService;
    private final CustomCookie cookie;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("# JwtVerificationFilter");
        cookie.createCookie(request, response);

//        check(request);
        try {
            Map<String, Object> claims = verifyAuthorizationJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) { // signature 에러
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) { // 기간 만료
            request.setAttribute("exception", ee);

            /*
                 access-Token의 유효기간이 만료되었을 때,
                 refresh-Token을 이용해서 access-Token 재발행
                 단, refresh-Token이 만료되면 재로그인 필요
            */

            verifyRefreshToken(request, response);

        } catch (Exception e) { // 그 외의 에러
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // refresh 토큰 유효성 검사
    protected void verifyRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // refresh 토큰 유효성 검사
            Map<String, Object> refreshClaims = verifyRefreshJws(request);
            Token token = verifyClaims(refreshClaims);

            // 새로운 AccessToken 발생 및 전달
//            String accessToken = delegateAccessTokenByRefreshToken(refreshClaims, token);
//            response.setHeader("Authorization", accessToken);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
            System.out.println("!! RefreshToken의 signiture와 payload가 불일치하면 동작");
        } catch (ExpiredJwtException eee) {
            request.setAttribute("exception", eee);
            System.out.println("!! RefreshToken 유효기간 종료");
        } catch (Exception e) {
            request.setAttribute("exception", e);
            System.out.println("!! RefreshToken의 header 불일치하면 동작");
        }
    }

    // access token 재발급 API를 위한 메서드
    public void assignAccessToken(HttpServletRequest request, HttpServletResponse response) {
        // refresh 토큰 유효성 검사
        Map<String, Object> refreshClaims = verifyRefreshJws(request);
        Token token = verifyClaims(refreshClaims);

        // 새로운 AccessToken 발생 및 전달
        String accessToken = delegateAccessTokenByRefreshToken(refreshClaims, token);
        response.setHeader("Authorization", accessToken);
    }

    // refresh-Token을 이용한 access-Token 재발행
    private String delegateAccessTokenByRefreshToken(Map<String, Object> refreshClaims, Token token) {
        // 새로운 Access 토큰 발행
        String subject = (String) refreshClaims.get("sub");
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", token.getRoles());

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        String accessToken = "Bearer " + jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        // 새로 발행된 access-Token 업데이트
        token.setAccessToken(accessToken);
        tokenService.patchToken(token);

        return accessToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        System.out.println("!! Authorization : " + authorization);

        return authorization == null || !authorization.startsWith("Bearer");
    }

    // access token 유효성 검증
    public Map<String, Object> verifyAuthorizationJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        verifyClaims(claims);
        return claims;
    }

    // refresh token 유효성 검증
    public Map<String, Object> verifyRefreshJws(HttpServletRequest request) {
        String jws = request.getHeader("Refresh");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    // token의 값이 실제 DB에 존재하는지 검사
    public Token verifyClaims(Map<String, Object> claims) {
        // token의 subject 유효성 검사
        String subject = (String) claims.get("sub");
        Token token;
        if (subject.contains("@")) { // 구글 로그인 - 이메일
            SellMember sellMember = sellMemberService.verifiedByEmail(subject);
            if (sellMember == null) {
                PurchaseMember purchaseMember = purchaseMemberService.verifiedByEmail(subject);
                if (purchaseMember == null) throw new BusinessLogicException(ExceptionCode.TAMPERED_TOKEN);
                token = purchaseMember.getToken();
            } else token = sellMember.getToken();
        } else { // 로그인 - 아이디
            token = tokenService.verifiedLoginId(subject);
        }

        // refresh token의 roles 유효성 검사
        if(claims.get("roles") != null) {
            List<String> roles = token.getRoles();
            List<String> claimsRoles = (List) claims.get("roles");
            if (!roles.containsAll(claimsRoles)) throw new BusinessLogicException(ExceptionCode.TAMPERED_TOKEN);
        }

        return token;
    }

    public void setAuthenticationToContext(Map<String, Object> claims) {
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List) claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("sub"), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // access-Token을 이용해서 user 정보 조회하기
//    private void check(HttpServletRequest request) {
//        String token = request.getHeader("access-Token");
//        String newToken = (String) request.getAttribute("new-access-Token");
//        Optional<PurchaseMember> user = tokenService.findByToken(token);
//        Optional<PurchaseMember> newUser = tokenService.findByToken(newToken);
//
//        if(newToken == null) user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PURCHASE_MEMBER_NOT_FOUND));
//        else newUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PURCHASE_MEMBER_NOT_FOUND));
//    }
}