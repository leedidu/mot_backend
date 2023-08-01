package com.umc.mot.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SearchResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String context;
    }
}
