package com.umc.mot.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

public class TokenResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; // 식별아이디
        private String loginId;
    }

    @Getter
    @AllArgsConstructor
    public static class check {
        // 사용가능한 아이디 여부(true : 사용가능, false : 사용 불가능)
        // ture : 인증번호 확인 완료, false : 인증번호 불인치
        private boolean check;
    }

    @Getter
    @AllArgsConstructor
    public static class sendMessage {
        private String phoneNumber;
        private int randomNumber;

    }
}
