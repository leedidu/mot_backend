package com.umc.mot.account.dto;

import com.umc.mot.sellMember.entity.SellMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

public class AccountRequestDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        private int id;
        private String name;
        private String bank;
        private String number;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String name;
        private String bank;
        private String number;
    }
}
