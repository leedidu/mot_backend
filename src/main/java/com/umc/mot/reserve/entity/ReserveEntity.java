package com.umc.mot.reserve.entity;

import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReserveEntity extends Auditable {

    @Id
    private int Id; //예약 식별아이디

    @Column
    private LocalDate CheckIn; //체크인

    @Column
    private LocalDate CheckOut; //체크아웃

    @Column
    private String Phone; //핸드폰

    @Column
    private int PeopleNum; //예약인원
}
