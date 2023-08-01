package com.umc.mot.hotel.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class HotelEntity {
    @Id
    private int hotelId;

    @Column
    private int maxPeople;

    @Column
    private int minPeople;

    @Column
    private int hotelPrice;

    @Column
    private String hotelName;

    @Column
    private int star; //별점

    @Column
    private String transfer; //이동수단

    @Column
    private String hotelAddress; //주소

    @Column
    private String info; //기본정보





}
