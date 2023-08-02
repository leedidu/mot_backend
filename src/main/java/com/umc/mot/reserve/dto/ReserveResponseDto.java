package com.umc.mot.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

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
}
