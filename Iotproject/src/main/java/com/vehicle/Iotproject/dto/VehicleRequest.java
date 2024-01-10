package com.vehicle.Iotproject.dto;


import com.vehicle.Iotproject.model.Fleet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Builder
public class VehicleRequest {


    @NotBlank(message = "Please enter the registration number ")
    @Pattern(regexp = "^[A-Z]{2}\\s[0-9]{2}\\s[A-Z]{2}\\s[0-9]{1,4}$", message = " Registration Number should be in this format AB 12 GH 1234 ")
    private String registrationNumber;

    @NotBlank(message = " Add the model of the vehicle ")
    private String model;

    @NotBlank(message = " Add style of the vehicle ")
    private String style;

    @NotBlank(message = " Add route of the vehicle ")
    private String route;

    private Fleet fleet;

    public VehicleRequest(String registrationNumber, String model, String style, String route, Fleet fleet) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.style = style;
        this.route = route;
        this.fleet = fleet;
    }
}
