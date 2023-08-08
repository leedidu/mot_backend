package com.umc.mot.token.controller;


import com.umc.mot.oauth2.filter.JwtVerificationFilter;
import com.umc.mot.token.dto.LoginByPhoneDto;
import com.umc.mot.token.dto.TokenRequestDto;
import com.umc.mot.token.dto.TokenResponseDto;
import com.umc.mot.token.dto.SigninDto;
import com.umc.mot.token.entity.CertificationPhone;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.mapper.TokenMapper;
import com.umc.mot.token.service.TokenService;
import com.umc.mot.utils.SendMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
//@RequestMapping("/token")
@Validated
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final SendMessage sendMessage;
    private final TokenMapper tokenMapper;
    private final JwtVerificationFilter jwtVerificationFilter;

    // TEST
    @GetMapping("/test")
    public ResponseEntity test() {
        Token token = tokenService.getLoginToken();

        System.out.println("##");
        System.out.println(token.getId());
        System.out.println(token.getLoginId());
        System.out.println("##");


        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Create
    // 회원가입
    @PostMapping("/signin")
    public ResponseEntity postToken(@Valid @RequestBody SigninDto signinDto) {
        Token token = tokenService.createToken(tokenMapper.signinDtoToToken(signinDto), signinDto.getPhone());
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDtoResponse(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 로그인 - 전화번호
    @PostMapping("/login/phone")
    public ResponseEntity postLoginByPhone(@Valid @RequestBody LoginByPhoneDto loginByPhoneDto, HttpServletResponse servletResponse) {
        Token token = tokenMapper.loginByPhoneDtoToToken(loginByPhoneDto);
        token = tokenService.findByPhone(token, loginByPhoneDto.getPhone(), loginByPhoneDto.getRandomNumber());

        TokenResponseDto.Check response = new TokenResponseDto.Check(false); // true : 일치하는 계정 존재, false : 일치하는 계정 없음
        if(token.getId() != 0) { // 일치하는 계정 존재
            response = new TokenResponseDto.Check(true);
            servletResponse.setHeader("Authorization", jwtVerificationFilter.delegateAccessToken(token));
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 인증문자 전송
    @PostMapping("/send-message/{phone-number}")
    public ResponseEntity postMessage(@PathVariable("phone-number") String phoneNumbe) {
        CertificationPhone certificationPhone =  sendMessage.sendMessage(phoneNumbe);
        TokenResponseDto.SendMessage response = tokenMapper.certificationPhoneToTokenResponseDtoSendMessage(certificationPhone);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    // 아이디 중복 확인
    @GetMapping("/check-login-id/{loginId}")
    public ResponseEntity getCheckLoginId(@PathVariable("loginId") String loginId) {
        TokenResponseDto.Check response = new TokenResponseDto.Check(tokenService.checkLoginId(loginId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 인증번호 확인
    @GetMapping("/check-random-number")
    public ResponseEntity getCheckRandomNumber(@Valid @RequestBody TokenRequestDto.CheckRandomNumber request) {
        CertificationPhone certificationPhone = tokenMapper.tokenRequestDtoCheckRandomNumberToCertificationPhone(request);
        TokenResponseDto.Check response = new TokenResponseDto.Check(sendMessage.checkCertificationNumber(certificationPhone));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 아아디 찾기(전화번호 이용)
    @GetMapping("/find-login-id")
    public ResponseEntity getFindLoginIdByPhoneNumber(@Valid @RequestBody TokenRequestDto.CheckRandomNumber request) {
        CertificationPhone certificationPhone = tokenMapper.tokenRequestDtoCheckRandomNumberToCertificationPhone(request);
        Token token = tokenService.findLoginIdByPhoneNumber(certificationPhone);
        TokenResponseDto.FindLoingId response = tokenMapper.tokenToTokenResponseDtoFindLoginId(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 잔고 확인(coolsms 서비스)
    @GetMapping("/get-balance")
    public ResponseEntity getBalance() {
        return new ResponseEntity<>(sendMessage.getBalance(), HttpStatus.OK);
    }


    // refresh token으로 access token 재발급
    @GetMapping("/assign-access-token")
    public ResponseEntity getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        jwtVerificationFilter.assignAccessToken(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getToken(@Positive @RequestParam int tokenId) {
        Token token = tokenService.findTokenId(tokenId);
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDtoResponse(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{token-id}")
    public ResponseEntity patchToken(@Positive @PathVariable("token-id") int tokenId,
                                      @RequestBody TokenRequestDto.Patch patch) {
        patch.setId(tokenId);
        Token token = tokenService.patchToken(tokenMapper.tokenRequestDtoPatchToToken(patch));
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDtoResponse(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/change-pw")
    public ResponseEntity patchChangePw(@Valid @RequestBody TokenRequestDto.ChangePw changePw) {
        tokenService.changePw(changePw.getLoginPw());

        return new ResponseEntity(HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{token-id}")
    public ResponseEntity deleteToken(@Positive @PathVariable("token-id") int tokenId) {
        tokenService.deleteToken(tokenId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
