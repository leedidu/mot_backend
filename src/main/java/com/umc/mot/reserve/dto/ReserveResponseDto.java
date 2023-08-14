package com.umc.mot.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ReserveResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; //예약 식별아이디
        private LocalDate checkIn; //체크인
        private LocalDate checkOut; //체크아웃
        private String phone; //핸드폰
        private int peopleNum; //예약인원
    }

    @Getter
    @AllArgsConstructor
    public static class Get{
        private List<Hotel> hotelInfo;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private List<Room> roomInfo;
        private List<Package> packageInfo;
    }

    @Getter
    @AllArgsConstructor
    public static class Room{
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class Package{
        private String name;
        private String roomType;
    }

    @Getter
    @AllArgsConstructor
    public static class Hotel{
        private String name;
        private int star;
        private int commentCount;
    }
}
