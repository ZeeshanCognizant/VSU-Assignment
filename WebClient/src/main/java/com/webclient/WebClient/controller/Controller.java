package com.webclient.WebClient.controller;


import com.vehicle.Iotproject.exception.DriverNotFoundException;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.model.Driver;
import com.webclient.WebClient.entity.DriverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
public class Controller {

    @Autowired
    WebClient webClient;




//    @GetMapping("/webclients/{id}")
//    ResponseEntity<DriverResponse> getDriverById(@PathVariable("id") int driverId) {
//
//        DriverResponse driverResponse = webClient
//                .get()
//                .uri("/api/drivers/{id}", driverId)
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError, response ->
//                         Mono.error(new DriverNotFoundException("Driver for id " +driverId+ " Not found",ErrorCode.DRIVER_NOT_FOUND)))
//                .bodyToMono(DriverResponse.class).block();
//
//        return new ResponseEntity<>(driverResponse, HttpStatus.FOUND);
//    }

    @GetMapping("/webclients/{id}")
    ResponseEntity<DriverResponse> getDriverById(@PathVariable("id") int driverId) {

        DriverResponse driverResponse = webClient.get()
                .uri("/api/drivers/{id}", driverId)
                .exchangeToMono(response -> {
                    if (response.statusCode().isError()) {
                        return Mono.error(new DriverNotFoundException("Driver Not Found !!!", ErrorCode.DRIVER_NOT_FOUND));
                    } else {
                        return response.bodyToMono(DriverResponse.class);
                    }
                }).block();

        return new ResponseEntity<>(driverResponse, HttpStatus.FOUND);
    }

//    @GetMapping("/webclients")
//    ResponseEntity<List<Driver>> getDriverList() {
//
//        List<Driver> drivers = webClient.get()
//                .uri("/api/drivers")
//                .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError,
//                        error -> Mono.error(new GeneralException("Driver List Not Found", ErrorCode.DRIVER_LIST_NOT_FOUND, HttpStatus.NOT_FOUND)))
//                .bodyToFlux(Driver.class)
//                .collectList()
//                .block();
//        return ResponseEntity.ok(drivers);
//    }


    @GetMapping("/webclients")
    ResponseEntity<List<DriverResponse>> getDriverList() {

        List<DriverResponse> list = webClient.get()
                .uri("/api/drivers")
                .exchangeToFlux(response -> {
                    if (response.statusCode().isError()) {
                        return Flux.error(new GeneralException("Driver List Not Found !!!", ErrorCode.DRIVER_LIST_NOT_FOUND, HttpStatus.NOT_FOUND));
                    } else
                        return response.bodyToFlux(DriverResponse.class);
                })
                .collectList()
                .block();

        return new ResponseEntity<>(list,HttpStatus.FOUND);
    }


}
