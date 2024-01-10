package com.webclient.WebClient.advice;

import com.vehicle.Iotproject.exception.DriverNotFoundException;
import com.vehicle.Iotproject.exception.ErrorResponse;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({VehicleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleVehicleNotFoundException(VehicleNotFoundException vehicleNotFoundException) {
//        ErrorResponse errorResponse =new ErrorResponse(HttpStatus.NOT_FOUND.value(), vehicleNotFoundException.getMessage(), LocalDateTime.now());
        ErrorResponse build = ErrorResponse.builder().errorCode(vehicleNotFoundException.getErrorCode().getValue())
                .errorMessage(vehicleNotFoundException.getErrorCode())
                .description(vehicleNotFoundException.getMessage())
                .localDateTime(String.valueOf(LocalDateTime.now())).build();
        return new ResponseEntity<ErrorResponse>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(MethodArgumentNotValidException methodArgumentNotValidException) {
        ErrorResponse build = ErrorResponse.builder().errorCode((HttpStatus.BAD_REQUEST.value()))
                .description((methodArgumentNotValidException.getBindingResult().getFieldErrors().get(0).getDefaultMessage()))
                .localDateTime(String.valueOf(LocalDateTime.now())).build();
        return new ResponseEntity<ErrorResponse>(build, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DriverNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleDriverNotFoundException(DriverNotFoundException driverNotFoundException) {
        ErrorResponse build = ErrorResponse.builder().errorCode((driverNotFoundException.getErrorCode().getValue()))
                .errorMessage(driverNotFoundException.getErrorCode())
                .description(driverNotFoundException.getMessage())
                .localDateTime(String.valueOf(LocalDateTime.now())).build();
        return new ResponseEntity<ErrorResponse>(build, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({GeneralException.class})
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException generalException) {
        ErrorResponse build = ErrorResponse.builder().errorCode((generalException.getErrorCode().getValue()))
                .errorMessage(generalException.getErrorCode())
                .description(generalException.getErrorCode().getReasonPhrase())
                .localDateTime(String.valueOf(generalException.getTimestamp())).build();
        return new ResponseEntity<ErrorResponse>(build, generalException.getHttpStatus());
    }

}
