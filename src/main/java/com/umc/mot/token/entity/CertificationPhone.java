package com.umc.mot.token.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CertificationPhone {
    @Id
    private String phoneNumber; // 01012341234 형태

    @Column
    private int randomNumber;
}
