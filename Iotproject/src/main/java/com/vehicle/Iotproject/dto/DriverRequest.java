package com.vehicle.Iotproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRequest {

    private int driverId;

    @NotEmpty
    @Size(min = 4, message = " Name must be minimum 4 characters ")
    private String name;

    @NotEmpty(message = " Please enter the address ")
    private String address;

    @NotEmpty(message = " Licence number should not be blank ")
    @Pattern(regexp = "^([A-Z]{2}[0-9]{2})( )[0-9]{11}$", message = " Licence Number should be in this format UP14 20160034761 ")
    private String licenceNumber;

    @NotEmpty
//    @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = " Mobile number should be start from 6,7,8,9 and it should have 10 digits ")
//    @Pattern(regexp = "^((\\+91?)|\\+)?[6-9]{1}[0-9]{9}$", message = " Mobile number should be start from 6,7,8,9 and it should have 10 digits ")
    @Pattern(regexp = "^(\\+91)[6-9]{1}[0-9]{9}$", message = " Mobile number should have 10 digits or it should start with prefix +91 should be start from 6,7,8,9.")
    private String phoneNumber;


    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private double Score;

    public DriverRequest(String name, String address, String licenceNumber, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.licenceNumber = licenceNumber;
        this.phoneNumber = phoneNumber;
    }
}
