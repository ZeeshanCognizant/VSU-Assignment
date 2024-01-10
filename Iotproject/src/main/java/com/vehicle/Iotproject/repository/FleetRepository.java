package com.vehicle.Iotproject.repository;

import com.vehicle.Iotproject.model.Fleet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FleetRepository extends JpaRepository<Fleet, Integer> {
    Optional<Fleet> findByRoute(@Param("route") String route);

}
