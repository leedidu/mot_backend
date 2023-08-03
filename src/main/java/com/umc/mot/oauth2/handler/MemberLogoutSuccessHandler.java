package com.umc.mot.oauth2.handler;

import com.umc.mot.purchaseMember.service.PurchaseMemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberLogoutSuccessHandler implements LogoutSuccessHandler {
    private final PurchaseMemberService memberService;


    public MemberLogoutSuccessHandler(PurchaseMemberService userService) {
        this.memberService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        System.out.println("!! success logout");

        UserEntity user = memberService.findByToken(request);
        user.setToken("");
        user.setRefresh("");
        memberService.updateUser(user);
    }
}
