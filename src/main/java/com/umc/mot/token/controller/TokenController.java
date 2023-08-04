package com.umc.mot.token.controller;


import com.umc.mot.token.dto.TokenRequestDto;
import com.umc.mot.token.dto.TokenResponseDto;
import com.umc.mot.token.dto.SigninDto;
import com.umc.mot.token.entity.Token;
import com.umc.mot.token.mapper.TokenMapper;
import com.umc.mot.token.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/check")
@Validated
@AllArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    private final TokenMapper tokenMapper;

    // Create - 회원가입
    @PostMapping
    public ResponseEntity postToken(@Valid @RequestBody SigninDto signinDto) {
        Token token = tokenService.createToken(tokenMapper.signinDtoToToken(signinDto), signinDto.getPhone());
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDto(token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Read
    @GetMapping("/{loginId}")
    public ResponseEntity getCheckId(@PathVariable("loginId") String loginId) {
        TokenResponseDto.checkId response = new TokenResponseDto.checkId(tokenService.useIdCheck(loginId));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getToken(@Positive @RequestParam int tokenId) {
        Token token = tokenService.findTokenId(tokenId);
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDto(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Update
    @PatchMapping("/{token-id}")
    public ResponseEntity patchToken(@Positive @PathVariable("token-id") int tokenId,
                                      @RequestBody TokenRequestDto.Patch patch) {
        patch.setId(tokenId);
        Token token = tokenService.patchToken(tokenMapper.tokenRequestDtoPatchToToken(patch));
        TokenResponseDto.Response response = tokenMapper.tokenToTokenResponseDto(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Delete
    @DeleteMapping("/{token-id}")
    public ResponseEntity deleteToken(@Positive @PathVariable("token-id") int tokenId) {
        tokenService.deleteToken(tokenId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
