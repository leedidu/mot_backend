package com.umc.mot.exception;

import com.umc.mot.room.entity.Room;
import lombok.Getter;

public enum ExceptionCode {
    SELL_MEMBER_NOT_FOUND(404, "Sell Member not found"),
    MESSAGE_NOT_FOUND(404, "Message not found"),
    ROOM_NOT_FOUND(404, "Message not found"),
    RESERVE_NOT_FOUND(404, "Reserve not found"),
    PACKAGE_NOT_FOUND(404, "Package not found"),
    HOTEL_NOT_FOUND(404,"Hotel not found"),
    HEART_NOT_FOUND(404,"Heart not found"),
    COMMENT_NOT_FOUND(404,"Comment not found"),
    CATEGORY_NOT_FOUND(404,"Category not found"),
    ROOM_MEMBER_NOT_FOUND(404,"Room_Member not found"),
    PURCHASE_MEMBER_NOT_FOUND(404,"Purchase_member not found");

    @Getter
    private double status;

    @Getter
    private String message;

    ExceptionCode(double code, String message) {
        this.status = code;
        this.message = message;
    }
}
