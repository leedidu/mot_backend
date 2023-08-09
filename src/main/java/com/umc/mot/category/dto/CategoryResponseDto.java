package com.umc.mot.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class CategoryResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String name;
        private String type;
    }

    @Getter
    @AllArgsConstructor
    public static class Responses {
        private List<Response> categories;
    }
}
