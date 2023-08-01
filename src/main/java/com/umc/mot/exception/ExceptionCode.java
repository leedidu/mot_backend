package com.umc.mot.exception;

import lombok.Getter;

public enum ExceptionCode {
    SELL_MEMBER_NOT_FOUND(404, "Sell Member not found"),
    MESSAGE_NOT_FOUND(404, "Message not found");

    @Getter
    private double status;

    @Getter
    private String message;

    ExceptionCode(double code, String message) {
        this.status = code;
        this.message = message;
    }
}
