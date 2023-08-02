package com.umc.mot.hotel.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.hotelCategory.entity.HotelCategory;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.sellMember.entity.SellMember;
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
public class Hotel extends Auditable { // 숙소

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String address; //주소

    @Column
    private String info; //기본정보

    @Column
    private int commentCount; // 댓글개수

    @Column
    private String distance; // 숙소이동거리

    @ManyToOne
    @JoinColumn(name = "SELLMEMBER_ID")
    private SellMember sellMember;

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<HotelCategory> hotelCategories = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Package> packages = new ArrayList<>();

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Room> rooms = new ArrayList<>();
}
