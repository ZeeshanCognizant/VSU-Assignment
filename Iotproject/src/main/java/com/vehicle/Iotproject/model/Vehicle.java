package com.vehicle.Iotproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Vehicle_Details")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int vehicleId;

    @NotBlank(message = " Please enter the registration number ")
    @Column(name="registration_number")
    private String registrationNumber;

    @NotBlank(message = " Add the model of the vehicle ")
    private String model;

    @NotBlank(message = " Add style of the vehicle ")
    private String style;

    @ManyToOne
    @JoinColumn(name = "fleet_id")
    @JsonIgnoreProperties("vehicleList")
    private Fleet fleet;

}
