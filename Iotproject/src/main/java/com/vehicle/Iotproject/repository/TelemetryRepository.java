package com.vehicle.Iotproject.repository;

import com.vehicle.Iotproject.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Integer> {

    List<Telemetry> findAllByTimeStampBetween(LocalDateTime startTime, LocalDateTime endTime);


}
