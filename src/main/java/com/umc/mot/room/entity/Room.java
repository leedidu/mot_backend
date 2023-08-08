package com.umc.mot.room.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.roomPackage.entity.RoomPackage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //객실 식별자

    @Column(nullable = false, length = 30)
    private String name; //객실 이름

    @Column
    @Min(value = 1, message = "최소 인원은 1 이상이어야 합니다.")
    private int minPeople;//최소인원

    @Column
    @Max(value =999 , message="최대 인원 999 이하이어야 합니다.")
    private int maxPeople; //최대인원

    @Column
    private int price; //비용

    @Column(nullable = false, length = 30)
    private String roomType; //객실방종류

    @Column(nullable = false, length = 100)
    private String info; //기본정보

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "RESERVE_ID")
    private Reserve reserve;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<RoomPackage> roomPackages = new ArrayList<>();
}
