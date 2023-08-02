package com.umc.mot.packagee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

public class PackageResponseDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private int id; //패키지 식별자
        private String name; //패키지 이름
        private int minPeople;
        private int maxPeople;
        private int price;
    }

}
