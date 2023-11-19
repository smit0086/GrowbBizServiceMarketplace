package com.growbiz.backend.Exception.models;


public enum ErrorMessages {
    USER_ALREADY_EXIST(41001),
    USERNAME_NOT_FOUND(41002),
    USERNAME_PASSWORD_INCORRECT(41003),
    BUSINESS_ALREADY_EXIST(42001),
    BUSINESS_NOT_FOUND(42002),
    CATEGORY_ALREADY_EXIST(43101),
    CATEGORY_NOT_FOUND(43102),
    SUBCATEGORY_ALREADY_EXIST(43201),
    SUBCATEGORY_NOT_FOUND(43202),
    SERVICE_NOT_FOUND(44001),
    SERVICE_ALREADY_EXISTS(44002),
    REVIEW_RATING_ALREADY_EXISTS(45001),
    REVIEW_RATING_NOT_FOUND(45002);


    private final int errorCode;

    ErrorMessages(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getValue() {
        return this.errorCode;
    }

}
