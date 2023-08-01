package com.umc.mot.packagee.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Package extends Auditable { //패키지

    @Id
    private int id; //패키지 식별자

    @Column
    private String name; //패키지 이름

    @Column
    private int minPeople;

    @Column
    private int maxPeople;

    @Column
    private int price;





}
