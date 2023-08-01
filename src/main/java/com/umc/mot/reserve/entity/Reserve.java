package com.umc.mot.reserve.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
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
}
