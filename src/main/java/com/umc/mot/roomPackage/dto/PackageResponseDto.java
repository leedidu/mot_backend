package com.umc.mot.roomPackage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class PackageResponseDto  {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private int id; //패키지 식별자
        private String name; //패키지 이름
        private int minPeople;
        private int maxPeople;
        private int price;
        private String roomType; //패키지방종류
        private String info; //기본정보
        private List<String> photo;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }

}
