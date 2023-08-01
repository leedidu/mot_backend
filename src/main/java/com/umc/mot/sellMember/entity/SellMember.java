package com.umc.mot.sellMember.entity;


import com.umc.mot.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SellMember extends Auditable{

    @Id
    private int sellMemberId;  //판매자회원식별자

    @Column
    private int id; //회원 아이디

    @Column
    private String name; //회원 이름

    @Column
    private String imageUrl; //회원이미지

    @Column
    private String email; //회원이메일

    @Column
    private String phone; //회원 전화번호

    @Column
    private String pw; //회원 비밀번호

    @Column
    private String host; //회원역할

    @Column
    private String token; //회원 토큰


}
