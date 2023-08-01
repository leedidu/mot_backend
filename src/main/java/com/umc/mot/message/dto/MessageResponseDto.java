package com.umc.mot.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class MessageResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int messageId; //답글아이디
        private String content; //내용
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private int messageId; //답글아이디
        private String content; //내용
    }
}
