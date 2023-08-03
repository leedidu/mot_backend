package com.umc.mot.oauth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.mot.oauth2.jwt.JwtTokenizer;
import com.umc.mot.oauth2.login.LoginDto;
import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {  // (1)
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final PurchaseMemberService memberService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtTokenizer jwtTokenizer,
                                   PurchaseMemberService memberService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenizer = jwtTokenizer;
        this.memberService = memberService;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("!! attemptAuthentication");

        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

//        System.out.println("!! make objectMapper");
//        System.out.println("!! inputStream : " + request.getInputStream().readAllBytes());
//        System.out.println("!! " + getBody(request));
//        String result = getBody(request);
//        String[] results = result.split("\"");
//        for(int i=0; i<results.length; i++)
//            System.out.println(i+") " + results[i]);
//
//        LoginDto loginDto = new LoginDto();
//        try {
//            loginDto.setEmail(results[3]);
//            loginDto.setPassword(results[7]);
//        } catch (Exception e) {
////            double num = Math.random();
//            loginDto.setEmail("test@gamil.com");
//            loginDto.setPassword("q1q1Q!Q!");
//            User user = new User();
//            user.setEmail(loginDto.getEmail());
//            user.setPassword(loginDto.getPassword());
//            user.setName("test");
//            userService.createUser(user);
//        }
//        System.out.println("!! email : " + loginDto.getEmail());
//        System.out.println("!! pw : " + loginDto.getPassword());


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getLoginPw());

        return authenticationManager.authenticate(authenticationToken);
    }

    public String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                System.out.println("!! inputStream");
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    System.out.println("!1 char Buffer : " + charBuffer);
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException  {
        User user = (User) authResult.getPrincipal();

        String accessToken = delegateAccessToken(user);
        String refreshToken = delegateRefreshToken(user);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        // ------------------------------------------------------------
        // response.body에 내용 생성
        saveResponseBody(response, user);

        // userPage에 Authorization과 Refresh 설정하기
        memberService.saveTokenInUserPage(user.getUserPage().getUserPageId(), "Bearer " + accessToken, refreshToken);
        // ------------------------------------------------------------

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    private void saveResponseBody(HttpServletResponse response,
                                  User user)  throws IOException {
        System.out.println("!! saveResponseBody");

        // response.body 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        // User를 UserResponseDto로 변환
        MemberResopnse userResponse = new MemberResopnse(
                user.getUserId(),
                user.getName(),
                user.getEmail()
        );

        // json 형식으로 변환
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(userResponse);
        response.getWriter().write(result);
    }

    private String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", user.getRoles());

        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(User user) {
        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public class MemberResopnse {
        private long MemberId;
        private String name;
        private String email;
    }
}