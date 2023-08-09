package com.umc.mot.purchaseMember.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class PurchaseMemberRequestDto {

  @Getter
    @AllArgsConstructor
    public static class Post {

        private int purchaseMemberId;  //판매자회원식별자
        private String name; //회원 이름
        private String imageUrl; //회원이미지
        private String email; //회원이메일
        private String phone; //회원 전화번호
        private String host; //회원역할
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {

        private int purchaseMemberId;  //판매자회원식별자
        private String name; //회원 이름
        private String imageUrl; //회원이미지
        private String email; //회원이메일
        private String phone; //회원 전화번호
        private String host; //회원역할
    }
}
