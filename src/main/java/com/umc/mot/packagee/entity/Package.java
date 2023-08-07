package com.umc.mot.packagee.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.roomPackage.entity.RoomPackage;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "packages")
public class Package extends Auditable { //패키지

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //패키지 식별자

    @Column
    private String name; //패키지 이름

    @Column
    private int minPeople;

    @Column
    private int maxPeople;

    @Column
    private int price;

    @Column
    private String info; //기본정보


    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "RESERVE_ID")
    private Reserve reserve;

    @OneToMany(mappedBy = "packages", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<RoomPackage> roomPackages = new ArrayList<>();
}
