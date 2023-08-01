package com.umc.mot.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommentResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String context;
        private String imageUrl;
        private int star;
        private int memberId;
        private boolean visible; // true : 보임, false : 안보임
    }
}