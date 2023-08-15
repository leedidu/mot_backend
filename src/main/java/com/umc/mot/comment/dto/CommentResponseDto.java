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
        private boolean visible;// true : 보임, false : 안보임
        private int PURCHASE_MEMBER_ID;
        private int HOTEL_ID;

        private List<String> photos;
    }

}