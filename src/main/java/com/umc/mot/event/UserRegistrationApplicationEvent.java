package com.umc.mot.event;

import lombok.Getter;
import com.umc.mot.token.entity.Token;

@Getter
public class UserRegistrationApplicationEvent {
    private Token token;
    public UserRegistrationApplicationEvent(Token token) {
        this.token = token;
    }
}
