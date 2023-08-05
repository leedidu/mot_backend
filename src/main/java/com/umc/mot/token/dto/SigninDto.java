package com.umc.mot.token.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SigninDto {
    private String loginId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?<>:])[A-Za-z\\d~!@#$%^&*()+|=]{4,16}$",
            message = "특수문자는 1개 이상 들어가야 합니다, 비밀번호 '최소 4자에서 최대 16자'까지 허용")
    private String loginPw;

    @Pattern(regexp = "^010-?([0-9]{4})-?([0-9]{4})$",
            message = "전화번호 형태는 010-1234-1234 입니다.")
    private String phone;
}
