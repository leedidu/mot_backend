package com.umc.mot.hotel.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class HotelEntity extends Auditable{ //숙소
    @Id
    private int id; //숙소식별자

    @Column
    private int maxPeople; //최대인원

    @Column
    private int minPeople; //최소인원

    @Column
    private int price; //가격

    @Column
    private String name; //숙소이름

    @Column
    private int star; //별점

    @Column
    private String map; //지도

    @Column
    private String transfer; //이동수단

    @Column
    private String hotelAddress; //주소

    @Column
    private String info; //기본정보





}
