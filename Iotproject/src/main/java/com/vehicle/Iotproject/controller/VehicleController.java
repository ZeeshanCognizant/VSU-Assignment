package com.vehicle.Iotproject.controller;

import com.vehicle.Iotproject.Service.VehicleServices;
import com.vehicle.Iotproject.dto.VehicleRequest;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.exception.VehicleNotFoundException;
import com.vehicle.Iotproject.model.Vehicle;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleServices vehicleServices;

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> saveVehicle(@Valid @RequestBody VehicleRequest vehicleRequest) {
        return new ResponseEntity<Vehicle>(vehicleServices.saveVehicle(vehicleRequest), HttpStatus.CREATED);
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicle() {
        return ResponseEntity.ok(vehicleServices.getAllVehicle());
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> vehicleById(@PathVariable("id") int vehicleId) {
        return new ResponseEntity<>(vehicleServices.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @DeleteMapping("/vehicles/{id}")
    public ResponseEntity deleteVehicleById(@PathVariable("id") int vehicleId) {
      if(vehicleServices.deleteVehicleById(vehicleId))
          return ResponseEntity.ok(" Vehicle with id " +vehicleId+ " deleted Successfully");
      else
          throw new VehicleNotFoundException(" Vehicle with id " +vehicleId+ " Not Found", ErrorCode.VEHICLE_NOT_FOUND);

    }

}
