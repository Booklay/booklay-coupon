package com.nhnacademy.booklay.booklaycoupon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    // TODO 4 : HttpStatus.METHOD_NOT_ALLOWED ㅇㅋ?
    RESOURCE_NOT_FOUND(HttpStatus.METHOD_NOT_ALLOWED, "Element Not Found."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
