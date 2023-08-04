package com.umc.mot.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class TokenRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id; //예약 식별아이디
        private LocalDate checkIn; //체크인
        private LocalDate checkOut; //체크아웃
        private String phone; //핸드폰
        private int peopleNum; //예약인원
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id; //예약 식별아이디
        private LocalDate checkIn; //체크인
        private LocalDate checkOut; //체크아웃
        private String phone; //핸드폰
        private int peopleNum; //예약인원
    }
}
