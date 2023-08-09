package com.umc.mot.roomPackage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class RoomRequestDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        private int id; //객실 식별자
        private String name; //객실 이름
        private int minPeople;//최소인원
        private int maxPeople; //최대인원
        private int price; //비용
        private String info; //기본정보
        private String roomType; //객실방종류
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
