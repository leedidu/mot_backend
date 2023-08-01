package com.umc.mot.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class RoomResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; //객실 식별자
        private String name; //객실 이름
        private int minPeople;//최소인원
        private int maxPeople; //최대인원
        private int roomPrice; //비용
    }
}
