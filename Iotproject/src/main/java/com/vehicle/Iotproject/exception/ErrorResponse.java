package com.vehicle.Iotproject.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private int errorCode;

    private ErrorCode errorMessage;

    private String description;

    private String localDateTime;

}
