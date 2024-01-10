package com.webclient.WebClient.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverNotFoundException extends RuntimeException {


    public DriverNotFoundException(String message) {
        super(message);
    }

    private ErrorCode errorCode;

    public DriverNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
