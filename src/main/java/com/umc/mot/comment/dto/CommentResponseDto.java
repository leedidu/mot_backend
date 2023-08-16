package com.umc.mot.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class CommentResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String context;
        private int star;
        private List<String> photos;
        private boolean visible; // true : 보임, false : 안보임
    }
}