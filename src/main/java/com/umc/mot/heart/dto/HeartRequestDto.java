package com.umc.mot.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class HeartRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {


    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id; //좋아요식별아이디
    }
}
