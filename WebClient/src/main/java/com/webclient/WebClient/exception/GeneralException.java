package com.webclient.WebClient.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class GeneralException extends RuntimeException {

    public GeneralException(String message) {
        super(message);
    }

    private ErrorCode errorCode;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;

    public GeneralException(String message, ErrorCode errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }



}
