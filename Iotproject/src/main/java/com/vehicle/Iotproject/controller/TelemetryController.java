package com.vehicle.Iotproject.controller;

import com.vehicle.Iotproject.Service.TelemetryService;
import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.dto.DriverResponse;
import com.vehicle.Iotproject.model.Telemetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TelemetryController {

    @Autowired
    TelemetryService telemetryService;


    @PostMapping("/telemetry")
    public ResponseEntity<Telemetry> addTelemetry(@RequestBody Telemetry telemetry){
        return new ResponseEntity<>(telemetryService.saveTelemetry(telemetry), HttpStatus.CREATED);
    }

    @GetMapping("/telemetrys/{startTime}/{endTime}")
    public ResponseEntity<List<Telemetry>> telemetryByTimeRange(@PathVariable String startTime, @PathVariable String endTime){
        return new ResponseEntity<>(telemetryService.telemetryByTimeRange(startTime, endTime),HttpStatus.OK);
    }

    @GetMapping("/telemetrys/performance/{startTime}/{endTime}")
    public DriverRequest bestPerformer(@PathVariable String startTime, @PathVariable String endTime){
        return telemetryService.getBestPerformer(startTime, endTime).get();
    }

    @GetMapping("/telemetrys/last1hr")
    public ResponseEntity<List<DriverResponse>> driverScore(){
        return ResponseEntity.ok(telemetryService.getDriverScore());
    }
}
