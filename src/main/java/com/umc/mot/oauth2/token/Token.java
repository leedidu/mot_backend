package com.umc.mot.oauth2.jwt.token;

import com.umc.mot.purchaseMember.entity.PurchaseMember;
import com.umc.mot.sellMember.entity.SellMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String accessToken;

    @Column
    private String refreshToken;

    @Column
    private String loginId; // 로그인 ID

    @Column
    private String loginPw; // 로그인 PW

    @OneToOne
    @JoinColumn(name = "SELL_MEMBER_ID")
    private SellMember sellMember;

    @OneToOne
    @JoinColumn(name = "PURCHASE_MEMBER_ID")
    private PurchaseMember purchaseMember;
}
