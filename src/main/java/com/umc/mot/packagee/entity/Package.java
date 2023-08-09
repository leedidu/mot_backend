package com.umc.mot.packagee.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.roomPackage.entity.RoomPackage;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Column(nullable = false, length = 30)
    private String name; //패키지 이름

    @Column
    @Min(value =1 , message="최소 인원 1 이상이어야 합니다.")
    private int minPeople;

    @Column
    @Max(value =999 , message="최대 인원 999 이하이어야 합니다.")
    private int maxPeople;

    @Column
    private int price;


    @Column(nullable = false, length = 100)
    private String roomType; //패키지방종류

    @Column(nullable = false, length = 500)
    private String info; //기본정보

    @Column
    @ElementCollection
    private List<String> photo = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "RESERVE_ID")
    private Reserve reserve;

    @OneToMany(mappedBy = "packages", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<RoomPackage> roomPackages = new ArrayList<>();
}
