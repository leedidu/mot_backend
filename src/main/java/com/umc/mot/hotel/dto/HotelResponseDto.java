package com.umc.mot.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class HotelResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id; //숙소식별자
        private int maxPeople; //최대인원
        private int minPeople; //최소인원
        private int price; //가격
        private String name; //숙소이름
        private int star; //별점
        private String map; //지도
        private String transfer; //이동수단
        private String address; //주소
        private String info; //기본정보
    }
}
