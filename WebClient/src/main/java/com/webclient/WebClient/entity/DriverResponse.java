package com.webclient.WebClient.entity;

import jakarta.persistence.Column;
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

    @Column(name = "licence_number")
    private String licenceNumber;

    @Column(name = "phone_number")
    private String phoneNumber;
}
