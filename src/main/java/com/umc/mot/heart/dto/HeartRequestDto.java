package com.umc.mot.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class HeartRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id; //좋아요식별아이디
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private int id; //좋아요식별아이디
    }
}
