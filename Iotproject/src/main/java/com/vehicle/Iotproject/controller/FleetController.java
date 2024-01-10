package com.vehicle.Iotproject.controller;

import com.vehicle.Iotproject.Service.FleetServices;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.exception.VehicleNotFoundException;
import com.vehicle.Iotproject.model.Fleet;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class FleetController {

    @Autowired
    FleetServices fleetServices;

    @GetMapping("/fleets")
    public ResponseEntity<List<Fleet>> getAllFleet() {
        return ResponseEntity.ok(fleetServices.getFleetList());
    }

    @PostMapping("/fleet")
    public ResponseEntity<Fleet> saveFleet(@RequestBody @Valid Fleet fleet) {
        return new ResponseEntity<Fleet>(fleetServices.saveFleet(fleet), HttpStatus.CREATED);
    }

    @DeleteMapping("/fleets/{route}")
    public ResponseEntity deleteFleetByRoute(@PathVariable("route") String route) {
        if(fleetServices.deleteFleetByRoute(route))
            return ResponseEntity.ok(" Fleet with Route " +route+ " deleted Successfully");
        else
            throw new VehicleNotFoundException((" Fleet with Route " +route+ " Not Found"), ErrorCode.FLEET_NOT_FOUND);
    }

}
