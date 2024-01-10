package com.vehicle.Iotproject.Service;


import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.dto.DriverResponse;
import com.vehicle.Iotproject.model.Driver;
import com.vehicle.Iotproject.model.Telemetry;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.repository.DriverRepository;
import com.vehicle.Iotproject.repository.TelemetryRepository;
import com.vehicle.Iotproject.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TelemetryService {

    @Autowired
    TelemetryRepository telemetryRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverServices driverServices;

    public Telemetry saveTelemetry(Telemetry telemetry) {
        boolean vehicleIdAvailable = vehicleRepository.existsById(telemetry.getVehicleId());
        boolean driverIdAvailable = driverRepository.existsById(telemetry.getDriverId());

        if (!vehicleIdAvailable)
            throw new GeneralException(ErrorCode.VEHICLE_NOT_FOUND.getReasonPhrase(), ErrorCode.VEHICLE_NOT_FOUND,
                    HttpStatus.BAD_REQUEST);
        if (!driverIdAvailable)
            throw new GeneralException(ErrorCode.DRIVER_NOT_FOUND.getReasonPhrase(), ErrorCode.DRIVER_NOT_FOUND,
                    HttpStatus.BAD_REQUEST);
        telemetry.setTimeStamp(LocalDateTime.now());

        return telemetryRepository.save(telemetry);
    }

    public List<Telemetry> telemetryByTimeRange(String startTime, String endTime) {

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterFactory("yyyy-MM-dd HH:mm:ss").createDateTimeFormatter();
        LocalDateTime startTimeLocal = LocalDateTime.parse(startTime, dateTimeFormatter);
        LocalDateTime endTimeLocal = LocalDateTime.parse(endTime, dateTimeFormatter);

        return telemetryRepository.findAllByTimeStampBetween(startTimeLocal, endTimeLocal);
    }


    public Optional<DriverRequest> getBestPerformer(String startTime, String endTime) {

        Pair<LocalDateTime, LocalDateTime> dateTimeRange = convertDateToDateTime(startTime, endTime);
        List<Telemetry> telemetry = telemetryRepository.findAllByTimeStampBetween(dateTimeRange.getFirst(), dateTimeRange.getSecond());
        Pair<Integer, Double> driverScore = bestPerformanceDriver(telemetry);
        if (driverScore == null) {
            return Optional.empty();
        }
        Driver bestDriver = driverRepository.findById(driverScore.getFirst()).get();
        DriverRequest driverRequest = driverServices.driverToDriverRequest(bestDriver);
        driverRequest.setScore(driverScore.getSecond());
        return Optional.of(driverRequest);
    }

    public List<DriverResponse> getDriverScore() {

        LocalDateTime endTimeLocal = LocalDateTime.now();
        LocalDateTime startTimeLocal = endTimeLocal.minusHours(1L);

        List<Telemetry> telemetryList = telemetryRepository.findAllByTimeStampBetween(startTimeLocal, endTimeLocal);
        Map<Integer, Double> score = driverScore(telemetryList);
        List<Driver> driverList = driverRepository.findAllById(score.keySet());
        List<DriverResponse> driverResponseList = new ArrayList<>();
        for (Driver dl : driverList) {
            DriverResponse driverResponse = driverServices.driverToDriverResponse(dl);
            driverResponse.setScore(score.get(driverResponse.getDriverId()));
            driverResponseList.add(driverResponse);
        }
        return driverResponseList;
    }

    private Map<Integer, Double> driverScore(List<Telemetry> telemetryList) {

        Map<Integer, List<Telemetry>> listMap = telemetryList.stream().collect(Collectors.groupingBy(Telemetry::getDriverId));

        Map<Integer, Double> idScore = new HashMap<>();
        int driverId;
        for (Map.Entry<Integer, List<Telemetry>> te : listMap.entrySet()) {
            double driverScore = calculatedScore(te.getValue());
            driverId = te.getKey();
            idScore.put(driverId, driverScore);
        }
        return idScore;
    }

    private Pair<Integer, Double> bestPerformanceDriver(List<Telemetry> telemetryList) {

        Map<Integer, List<Telemetry>> telemetry = telemetryList.stream().collect(Collectors.groupingBy(Telemetry::getDriverId));
        double score = Integer.MIN_VALUE;
        Pair<Integer, Double> keyValue = null;
        int driverId;

        for (Map.Entry<Integer, List<Telemetry>> te : telemetry.entrySet()) {
            double calculateScore = calculatedScore(te.getValue());
            if (score < calculateScore) {
                score = calculateScore;
                driverId = te.getKey();
                keyValue = Pair.of(driverId, score);
            }
        }
        return keyValue;
    }


    private static Pair<LocalDateTime, LocalDateTime> convertDateToDateTime(String startTime, String endTime) {
        LocalDate from = LocalDate.parse(startTime, new DateTimeFormatterFactory("yyyy-MM-dd").createDateTimeFormatter());
        LocalDate to = LocalDate.parse(endTime, new DateTimeFormatterFactory("yyyy-MM-dd").createDateTimeFormatter());

        LocalDateTime startLocalDateTime = LocalDateTime.of(from, LocalTime.of(00, 00, 00));
        LocalDateTime endLocalDateTime = LocalDateTime.of(to, LocalTime.of(23, 59, 59));
        return Pair.of(startLocalDateTime, endLocalDateTime);
    }

    private int distanceParameterScore(Double distance) {
        if (distance <= 25) {
            return 2;
        } else if (distance <= 50) {
            return 5;
        } else if (distance <= 75) {
            return 7;
        } else if (distance <= 100) {
            return 10;
        } else
            return 10;
    }

    private double calculatedScore(List<Telemetry> telemetryList) {
        double score = 0.0;

        for (Telemetry te : telemetryList) {
            if (te.getParameter().equals("distance")) {
                score = score + distanceParameterScore(te.getParamValue());
            } else {
                score = score + te.getParamValue();
            }
        }
        return score;
    }

}
