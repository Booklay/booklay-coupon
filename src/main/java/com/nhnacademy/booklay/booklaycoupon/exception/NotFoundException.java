package com.nhnacademy.booklay.booklaycoupon.exception;

public class NotFoundException extends RuntimeException {
    // Todo 2 : 메시지는 이러함.
    public NotFoundException(String entityName, Long inputId) {
        super("**********Element Not Found**********"
            +"\n Entity Name : " + entityName
            +"\n Input ID : " + inputId);

    }
}
