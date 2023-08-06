package com.umc.mot.token.mapper;

import com.umc.mot.token.dto.LoginDto;
import com.umc.mot.token.dto.SigninDto;
import com.umc.mot.token.dto.TokenRequestDto;
import com.umc.mot.token.dto.TokenResponseDto;
import com.umc.mot.token.entity.CertificationPhone;
import com.umc.mot.token.entity.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenResponseDto.Response tokenToTokenResponseDto(Token token);
    TokenResponseDto.check tokenToTokenResponseCheckIdDto(Token token);
    Token tokenRequestDtoPostToToken(TokenRequestDto.Post post);
    Token tokenRequestDtoPatchToToken(TokenRequestDto.Patch patch);
    Token loginDtoToToken(LoginDto loginDto);
    Token signinDtoToToken(SigninDto signinDto);
    CertificationPhone tokenRequestDtoCheckRandomNumberToCertificationPhone(TokenRequestDto.CheckRandomNumber checkRandomNumber);
    TokenResponseDto.sendMessage certificationPhoneToTokenResponseDtoSendMessage(CertificationPhone certificationPhone);
}
