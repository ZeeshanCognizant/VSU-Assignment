package com.vehicle.Iotproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Driver_Details")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;

    private String name;

    private String address;

    @Column(name = "licence_number")
    private String licenceNumber;

    @Column(name = "phone_number")
    private String phoneNumber;


}
