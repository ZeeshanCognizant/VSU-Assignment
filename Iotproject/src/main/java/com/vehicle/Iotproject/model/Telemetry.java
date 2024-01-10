package com.vehicle.Iotproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telemetry_details")
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="vehicle_id")
    private int vehicleId;

    @Column(name="driver_id")
    private int driverId;

    private String parameter;

    @Column(name="param_value")
    private Double paramValue;

    @Column(name="timestamp")
    private LocalDateTime timeStamp;

}
