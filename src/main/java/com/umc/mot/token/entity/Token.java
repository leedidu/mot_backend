package com.umc.mot.token.entity;

import com.umc.mot.purchaseMember.entity.PurchaseMember;
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
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String accessToken = "";

    @Column
    private String refreshToken = "";

    @Column(unique = true)
    private String loginId; // 로그인 ID

    @Column
    private String loginPw; // 로그인 PW

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "SELL_MEMBER_ID")
    private SellMember sellMember;

    @OneToOne
    @JoinColumn(name = "PURCHASE_MEMBER_ID")
    private PurchaseMember purchaseMember;

    public String getPassword() {
        return this.loginPw;
    }
}
