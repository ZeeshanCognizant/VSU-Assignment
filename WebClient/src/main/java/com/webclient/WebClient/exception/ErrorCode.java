package com.webclient.WebClient.exception;

public enum ErrorCode {

    VEHICLE_NOT_FOUND(1001, "Vehicle Not Found"),
    DRIVER_NOT_FOUND(1002, "Driver Not Found"),
    DUPLICATE_REGISTRATION_NUMBER(1003, "Vehicle with same registration number already exists"),
    DUPLICATE_LICENCE_NUMBER(1004, "Driver with same licence number already exists"),
    FLEET_NOT_FOUND(1005, "Fleet Not Found"),
    VEHICLE_LIST_NOT_FOUND(1007,"Vehicle List Not Found"),
    DRIVER_LIST_NOT_FOUND(1008,"Driver List Not Found");

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    private final int value;
    private final String reasonPhrase;
    public int getValue() {
        return this.value;
    }
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
