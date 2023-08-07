package com.umc.mot.token.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LoginByPhoneDto {
    private String loginId;

    private String phone;
}
