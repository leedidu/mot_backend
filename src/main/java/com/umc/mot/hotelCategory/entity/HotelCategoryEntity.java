package com.umc.mot.hotelCategory.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class HotelCategoryEntity extends Auditable { //숙소_카테고리

    @Id
    private int id; //숙소_카테고리식별자

}
