package com.webclient.WebClient.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }

    private ErrorCode errorCode;

    public VehicleNotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
