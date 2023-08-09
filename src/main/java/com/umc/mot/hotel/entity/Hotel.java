package com.umc.mot.hotel.entity;


import com.umc.mot.auditable.Auditable;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.hotelCategory.entity.HotelCategory;
import com.umc.mot.packagee.entity.Package;
import com.umc.mot.reserve.entity.Reserve;
import com.umc.mot.room.entity.Room;
import com.umc.mot.sellMember.entity.SellMember;
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
@Entity
public class Hotel extends Auditable { // 숙소

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //숙소식별자

    @Column
    @Max(value = 999, message = "최대 인원은 100 이하여야 합니다.")
    private int maxPeople; //최대인원

    @Column
    @Min(value = 0, message = "최소 인원은 5 이상이어야 합니다.")
    private int minPeople; //최소인원

    @Column
    private int price; //가격

    @Column(length = 30)
    private String name; //숙소이름

    @Column
    private int star; //별점

    @Column(length = 100)
    private String map; //지도

    @Column(length = 30)
    private String transfer; //이동수단

    @Column(length = 30)
    private String address; //주소

    @Column(length=100)
    private String region; //지역

    @Column(length=100)
    private String addressInfo; //상세주소

    @Column(length = 30)
    private String info; //기본정보

    @Column
    private int commentCount; // 댓글개수

    @Column(length = 30)
    private String distance; // 숙소이동거리

    @Column
    private String photo;


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

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Reserve> reserves = new ArrayList<>();
}
