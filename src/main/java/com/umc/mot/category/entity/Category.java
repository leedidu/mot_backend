package com.umc.mot.category.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotelCategory.entity.HotelCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Getter
    public static enum Type {
        GROUP("그룹"),
        FILTER("그룹 이외");

        @Getter
        private String state;

        Type(String state) {this.state = state;}
    }

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<HotelCategory> hotelCategories = new ArrayList<>();
}
