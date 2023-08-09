package com.umc.mot.purchaseMember.entity;

import com.umc.mot.auditable.Auditable;
import com.umc.mot.comment.entity.Comment;
import com.umc.mot.heart.entity.Heart;
import com.umc.mot.token.entity.Token;
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
public class PurchaseMember extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int purchaseMemberId; //구매자 회원 식별자

    @Column
    private String name ; //회원 이름

    @Column
    private String imageUrl; //회원 이메일

    @Column
    private String email; // 회원 이메일

    @Column(unique = true)
    private String phone; //회원 전화번호 010-1234-1234 형태

    @Column
    private String host; // 회원 역할


    @OneToMany(mappedBy = "purchaseMember", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseMember", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseMember", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Reserve> reserves = new ArrayList<>();

    @OneToOne(mappedBy = "purchaseMember", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Token token;

    public PurchaseMember(String email, String name, String imageUrl) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
