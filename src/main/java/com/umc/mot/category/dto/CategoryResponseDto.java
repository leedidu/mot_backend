package com.umc.mot.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CategoryResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String name;
    }
}
