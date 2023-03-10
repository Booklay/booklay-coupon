package com.nhnacademy.booklay.booklaycoupon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.FORBIDDEN, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ILLEGAL_ARGUMENT_ERROR(HttpStatus.NOT_ACCEPTABLE, "Not acceptable. Illegal argument error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
