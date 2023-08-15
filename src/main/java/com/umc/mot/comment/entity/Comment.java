package com.umc.mot.comment.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.hotel.entity.Hotel;
import com.umc.mot.message.entity.Message;
import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.reserve.entity.Reserve;
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
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String context;

    @Column
    @ElementCollection
    private List<String> photos = new ArrayList<>();

    @Column
    private int star;

    @Column
    private boolean visible = true; // true : 보임, false : 안보임

    @OneToMany(mappedBy = "comment", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PURCHASE_MEMBER_ID")
    private PurchaseMember purchaseMember;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    @OneToOne
    @JoinColumn(name = "RESERVE_ID")
    private Reserve reserve;
}

