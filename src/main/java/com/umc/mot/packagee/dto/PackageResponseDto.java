package com.umc.mot.packagee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;

public class PackageResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        @Id
        private int id; //패키지 식별자

        @Column
        private String name; //패키지 이름

        @Column
        private int minPeople;

        @Column
        private int maxPeople;

        @Column
        private int price;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private int messageId; //답글아이디
        private String content; //내용
    }
}
