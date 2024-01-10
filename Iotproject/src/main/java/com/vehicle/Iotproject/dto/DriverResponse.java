package com.vehicle.Iotproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {

    private int driverId;

    private String name;

    private String address;

    private String licenceNumber;

    private String phoneNumber;

    private double score;
}
