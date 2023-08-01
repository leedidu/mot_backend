package com.umc.mot.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SearchRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id;
        private String context;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String context;
    }
}
