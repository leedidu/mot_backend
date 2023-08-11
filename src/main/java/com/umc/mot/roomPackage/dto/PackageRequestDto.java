package com.umc.mot.roomPackage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

public class PackageRequestDto{
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        private int id; //패키지 식별자
        private String name; //패키지 이름

    }
}
