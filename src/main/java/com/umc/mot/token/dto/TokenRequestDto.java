package com.umc.mot.token.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class TokenRequestDto {
    @Getter
    @AllArgsConstructor
    public static class CheckRandomNumber {
        private String phoneNumber;
        private int randomNumber;
    }

    @Getter
    @AllArgsConstructor
    public static class ChangePw {
        private int id;
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=?<>:])[A-Za-z\\d~!@#$%^&*()+|=]{4,16}$",
                message = "특수문자는 1개 이상 들어가야 합니다, 비밀번호 '최소 4자에서 최대 16자'까지 허용")
        private String loginPw;
    }

    @Getter
    @AllArgsConstructor
    public static class Post {
        private int id;
        private String accessToken;
        private String refreshToken;
        private String LoginId;
        private String LoginPw;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        private int id;
        private String accessToken;
        private String refreshToken;
        private String LoginId;
        private String LoginPw;

    }
}
