package com.umc.mot.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MessageResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; //답글아이디
        private String content; //내용
    }
}
