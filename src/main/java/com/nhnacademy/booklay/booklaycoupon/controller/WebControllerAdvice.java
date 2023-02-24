package com.nhnacademy.booklay.booklaycoupon.controller;


import com.nhnacademy.booklay.booklaycoupon.dto.ErrorResponse;
import com.nhnacademy.booklay.booklaycoupon.exception.CommonErrorCode;
import com.nhnacademy.booklay.booklaycoupon.exception.ErrorCode;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex) {
        printLoggingError(ex);
        ErrorCode errorCode = CommonErrorCode.ILLEGAL_ARGUMENT_ERROR;
        return handleWithMessage(errorCode, ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        printLoggingError(ex);
        ErrorCode errorCode = CommonErrorCode.RESOURCE_NOT_FOUND;
        return handleWithMessage(errorCode, ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllException(Exception ex) {
        printLoggingError(ex);
        log.warn("handleAllException", ex);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(errorCode);
    }

    /**
     * 서버에서 난 에러는 로그에 남기고, 클라이언트에게 보일 메시지를 따로 보내기 위하여.
     * @param message 클라이언트에 보일 메시지.
     */
    private ResponseEntity<Object> handleWithMessage(ErrorCode errorCode, String message) {

        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponseWithMessage(errorCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponseWithMessage(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(message)
            .build();
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
            .code(errorCode.name())
            .message(errorCode.getMessage())
            .build();
    }

    private void printLoggingError(Exception exception) {
        log.error("\nError Occurred"
                + "\nException  : {}"
                + "\nMessage : {} ",
            exception.getClass(), exception.getMessage());
    }
}
