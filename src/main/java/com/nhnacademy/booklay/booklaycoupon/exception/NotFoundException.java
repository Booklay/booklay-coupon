package com.nhnacademy.booklay.booklaycoupon.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityName, Long inputId) {
        super("**********Element Not Found**********"
            +"\n Entity Name : " + entityName
            +"\n Input ID : " + inputId);

    }
}
