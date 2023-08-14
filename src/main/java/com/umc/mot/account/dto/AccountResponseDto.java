package com.umc.mot.account.dto;

import com.umc.mot.category.dto.CategoryResponseDto;
import com.umc.mot.sellMember.entity.SellMember;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class AccountResponseDto {
    @Getter
    @AllArgsConstructor
    public static class Response {
        private int id;
        private String name;
        private String bank;
        private String number;
        private int sellMemberId; //판매자 아이디 보이게 하기

    }
}
