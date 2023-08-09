package com.umc.mot.packagee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class PackageRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private int id; //패키지 식별자
        private String name; //패키지 이름
        private int minPeople;
        private int maxPeople;
        private int price;
        private String info;
        private String roomType; //객실방종류
        private List<String> photo;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id; //패키지 식별자
        private String name; //패키지 이름
        private int minPeople;
        private int maxPeople;
        private int price;
        private String info;
        private String roomType; //객실방종류
        private List<String> photo;
    }
}
