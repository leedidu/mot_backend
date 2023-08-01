package com.umc.mot.reserve.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Reserve extends Auditable {

    @Id
    private int id; //예약 식별아이디

    @Column
    private LocalDate checkIn; //체크인

    @Column
    private LocalDate checkOut; //체크아웃

    @Column
    private String phone; //핸드폰

    @Column
    private int peopleNum; //예약인원

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "PURCHASE_MEMBER_ID")
    private PurchaseMember purchaseMember;
}
