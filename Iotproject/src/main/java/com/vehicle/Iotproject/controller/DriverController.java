package com.vehicle.Iotproject.controller;

import com.vehicle.Iotproject.Service.DriverServices;
import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.exception.DriverNotFoundException;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.model.Driver;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class DriverController {
    @Autowired
    private DriverServices driverServices;

    @PostMapping("/driver")
    public ResponseEntity<Driver> saveDriver(@RequestBody @Valid DriverRequest driverRequest) {
        return new ResponseEntity<Driver>(driverServices.saveDriver(driverRequest), HttpStatus.CREATED);
    }


    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getAllDriver() {
        return ResponseEntity.ok(driverServices.getAllDriver());
    }


    @GetMapping("/drivers/{id}")
    public ResponseEntity<Driver> driverById(@PathVariable("id") Integer driverId) {
        return new ResponseEntity<>(driverServices.getDriverById(driverId), HttpStatus.OK);
    }

    @DeleteMapping("drivers/{id}")
    public ResponseEntity deleteDriverById(@PathVariable("id") Integer driverId) {
        if (driverServices.deleteDriverById(driverId)) {
            return ResponseEntity.ok("Driver with id " + driverId + " Deleted Successfully");
        }
        throw new DriverNotFoundException("Driver with id " + driverId + " Not Found ", ErrorCode.DRIVER_NOT_FOUND);
    }

    @PutMapping("drivers/{id}")
    public ResponseEntity<Driver> updateDriverById(@RequestBody Driver driver) {
        return ResponseEntity.ok(driverServices.updateDriverById(driver));
    }

}
