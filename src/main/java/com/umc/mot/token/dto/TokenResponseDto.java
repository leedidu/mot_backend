package com.umc.mot.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class TokenResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; // 식별아이디
        private String loginId;
    }

    @Getter
    @AllArgsConstructor
    public static class checkId {
        private boolean useIdCheck; // 사용가능한 아이디 여부(true : 사용가능, false : 사용 불가능)
    }
}
