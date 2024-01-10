package com.vehicle.Iotproject.dto;

import com.vehicle.Iotproject.model.Fleet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {

    private int vehicleId;

    private String registrationNumber;

    private String model;

    private String style;

    private Fleet fleet;
}
