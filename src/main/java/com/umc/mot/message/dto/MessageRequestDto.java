package com.umc.mot.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class MessageRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id; //답글아이디
        private String content; //내용
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id; //답글아이디
        private String content; //내용
    }
}
