package com.umc.mot.hotelCategory.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.category.entity.Category;
import com.umc.mot.hotel.entity.Hotel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class HotelCategory extends Auditable { //숙소_카테고리

    @Id
    private int id; //숙소_카테고리식별자

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;
}
