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

    }
}
