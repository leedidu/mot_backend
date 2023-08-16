package com.umc.mot.account.entity;

import com.umc.mot.sellMember.entity.SellMember;
import com.umc.mot.token.entity.Token;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String bank;

    @Column
    private String number;

    @OneToOne
    @JoinColumn(name = "sellMemberId")
    private SellMember sellMember;




}
