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
    private String phoneNumber;

    @Column
    private int randomNumber;
}
