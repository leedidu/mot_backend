package com.umc.mot.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

public class RoomRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id; //객실 식별자
        private String name; //객실 이름
        private int minPeople;//최소인원
        private int maxPeople; //최대인원
        private int roomPrice; //비용
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private int id; //객실 식별자
        private String name; //객실 이름
        private int minPeople;//최소인원
        private int maxPeople; //최대인원
        private int roomPrice; //비용
    }
}
