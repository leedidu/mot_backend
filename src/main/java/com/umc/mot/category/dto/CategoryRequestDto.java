package com.umc.mot.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CategoryRequestDto {

    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id;
        private String name;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String name;
    }
}
