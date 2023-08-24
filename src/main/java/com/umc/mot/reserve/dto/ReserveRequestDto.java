package com.umc.mot.reserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

public class ReserveRequestDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private int id; //예약 식별아이디
        private LocalDate checkIn; //체크인
        private LocalDate checkOut; //체크아웃
        private String phone; //핸드폰
        private int peopleNum; //예약인원
        private String paymentPrice; // 지불금액
        private int hotelId; //호텔 식별자
        private List<Integer> roomId; // 객실 식별자
        private List<Integer> packageId; //패키지 식별자
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
        private String paymentPrice; //지불금액
    }
}
