package com.umc.mot.room.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.roomPackage.entity.RoomPackage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @Column
    private String name; //객실 이름

    @Column
    private int minPeople;//최소인원

    @Column
    private int maxPeople; //최대인원

    @Column
    private int roomPrice; //비용

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<RoomPackage> roomPackages = new ArrayList<>();
}
