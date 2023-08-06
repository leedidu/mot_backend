
package com.umc.mot.exception;
import lombok.Getter;

public enum ExceptionCode {
    SELL_MEMBER_NOT_FOUND(404, "Sell Member not found"),
    PURCHASE_MEMBER_NOT_FOUND(404, "Purchase Member not found"),
    TOKEN_MEMBER_NOT_FOUND(404, "Token Member not found"),
    TOKEN_EXITS(409, "Token exits"),
    MESSAGE_NOT_FOUND(404, "Message not found"),
    ROOM_NOT_FOUND(404, "Message not found"),
    RESERVE_NOT_FOUND(404, "Reserve not found"),
    PACKAGE_NOT_FOUND(404, "Package not found"),
    HOTEL_NOT_FOUND(404,"Hotel not found"),
    HEART_NOT_FOUND(404,"Heart not found"),
    COMMENT_NOT_FOUND(404,"Comment not found"),
    CATEGORY_NOT_FOUND(404,"Category not found"),
    CERTIFICATION_NUMBER(404, "Certification Number not found");

    @Getter
    private double status;

    @Getter
    private String message;

    ExceptionCode(double code, String message) {
        this.status = code;
        this.message = message;
    }
}
