package com.umc.mot.heart.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class HeartEntity extends Auditable { //좋아요

    @Id
    private int id; //좋아요식별아이디
}
