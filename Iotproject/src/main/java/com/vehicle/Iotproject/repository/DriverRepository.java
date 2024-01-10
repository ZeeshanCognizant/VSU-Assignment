package com.vehicle.Iotproject.repository;

import com.vehicle.Iotproject.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    Optional<Driver> findByLicenceNumber(@Param("licenceNumber") String licenceNumber);

}

