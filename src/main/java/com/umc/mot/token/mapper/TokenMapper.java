package com.umc.mot.token.mapper;

import com.umc.mot.token.dto.*;
import com.umc.mot.token.entity.CertificationPhone;
import com.umc.mot.token.entity.Token;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    TokenResponseDto.Response tokenToTokenResponseDtoResponse(Token token);
    TokenResponseDto.FindLoingId tokenToTokenResponseDtoFindLoginId(Token token);
    TokenResponseDto.Check tokenToTokenResponseCheckIdDto(Token token);
    Token tokenRequestDtoPostToToken(TokenRequestDto.Post post);
    Token tokenRequestDtoPatchToToken(TokenRequestDto.Patch patch);
    Token loginDtoToToken(LoginDto loginDto);
    Token signinDtoToToken(SigninDto signinDto);
    Token loginByPhoneDtoToToken(LoginByPhoneDto loginByPhoneDto);
    CertificationPhone tokenRequestDtoCheckRandomNumberToCertificationPhone(TokenRequestDto.CheckRandomNumber checkRandomNumber);
    TokenResponseDto.SendMessage certificationPhoneToTokenResponseDtoSendMessage(CertificationPhone certificationPhone);
}
