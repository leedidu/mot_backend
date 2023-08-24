package com.umc.mot.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    @Setter
    @AllArgsConstructor
    public static class Get{
        private HotelInfo hotelInfo;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private List<RoomInfo> roomInfo;
        private List<PackageInfo> packageInfo;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class RoomInfo{
        private String name;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class PackageInfo{
        private String name;
        private String roomType;
        private String photo;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class HotelInfo{
        private String name;
        private int star;
        private int commentCount;
        private String photo; // 예약내역 -> 호텔 사진
    }

    @Getter
    @AllArgsConstructor
    public static class ReserveInfo{
        private int id;
        private int peopleNum;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private List<RoomInfo> roomInfo;
        private List<PackageInfo> packageInfo;
        private String photo; // 예약내역 -> 호텔 사진
    }

    @Getter
    @AllArgsConstructor
    public static class DetailInfo{
        private LocalDate checkIn;
        private LocalDate checkOut;
        private String name;
        private String phone;
        private List<RoomInfo> roomInfo;
        private List<PackageInfo> packageInfo;
    }
}