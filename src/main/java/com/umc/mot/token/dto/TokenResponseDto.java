package com.umc.mot.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TokenResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; // 식별아이디
        private String loginId;
    }

    @Getter
    @AllArgsConstructor
    public static class FindLoingId {
        private int id; // 식별아이디
        private String loginId;
        private LocalDateTime createdAt;
    }

    @Getter
    @AllArgsConstructor
    public static class Check {
        // 사용가능한 아이디 여부(true : 사용가능, false : 사용 불가능)
        // ture : 인증번호 확인 완료, false : 인증번호 불인치
        private boolean check;
    }

    @Getter
    @AllArgsConstructor
    public static class SendMessage {
        private String phoneNumber;
        private int randomNumber;

    }
}
