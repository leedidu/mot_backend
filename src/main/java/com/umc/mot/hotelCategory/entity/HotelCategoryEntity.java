package com.umc.mot.hotelCategory.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class HotelCategoryEntity {

    @Id
    private int hotelCategoryId;

}
