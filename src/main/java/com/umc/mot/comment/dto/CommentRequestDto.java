package com.umc.mot.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

public class CommentRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id;
        private String context;
        private String imageUrl;
        private int star;
        private int memberId;
        private boolean visible; // true : 보임, false : 안보임
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String context;
        private String imageUrl;
        private int star;
        private int memberId;
        private boolean visible; // true : 보임, false : 안보임
    }
}
