package com.umc.mot.heart.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Heart extends Auditable { //좋아요
    @Id
    private int id; //좋아요식별아이디

    @ManyToOne
    @JoinColumn(name = "PURCHASE_MEMBER_ID")
    private PurchaseMember purchaseMember;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;
}
