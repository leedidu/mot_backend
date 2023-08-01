package com.umc.mot.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class HeartResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; //좋아요식별아이디
    }
}
