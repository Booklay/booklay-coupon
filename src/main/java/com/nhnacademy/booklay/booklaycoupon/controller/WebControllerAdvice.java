package com.nhnacademy.booklay.booklaycoupon.controller;


import com.nhnacademy.booklay.booklaycoupon.dto.ErrorResponse;
import com.nhnacademy.booklay.booklaycoupon.exception.CommonErrorCode;
import com.nhnacademy.booklay.booklaycoupon.exception.ErrorCode;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 애플리케이션에서 발생하는 전반적인 Error 처리를 위한 컨트롤러입니다.
 *
 * @author 조현진
 */
@Slf4j
@RestControllerAdvice
public class WebControllerAdvice extends ResponseEntityExceptionHandler {

    // TODO 3 : 에러 처리
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        // TODO 4 : 우선은 HttpStatus = Method Not Allowed로 설정.
        ErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;

        // TODO 5 : errorCode랑 NotFoundException에 들어간 message를 보냄.
        return handleWithMessage(errorCode, ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .build();
    }

    private ResponseEntity<Object> handleWithMessage(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeResponse(errorCode, message));
    }

    private ErrorResponse makeResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(message)
            .build();
    }
}
