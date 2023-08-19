package com.umc.mot.heart.dto;

import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class HeartResponseDto {

    @Getter
    @AllArgsConstructor
    public static class Response{
        private int id;
        private int PurchaseMemberId;
        private int HotelId;

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListResponse {
        private int id; //좋아요식별아이디
        private double star;
        private int commentCount;
        private String name;
        private String photo;
        private int price;


    }
}
