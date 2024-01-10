package com.vehicle.Iotproject.repository;

import com.vehicle.Iotproject.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    Optional<Vehicle> findByRegistrationNumber(@Param("registrationNumber") String registrationNumber);

    List<Vehicle> findByFleetFleetId(@Param("fleetId") Integer fleetId);

}
