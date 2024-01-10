package com.vehicle.Iotproject.Service;

import com.vehicle.Iotproject.model.Fleet;
import com.vehicle.Iotproject.model.Vehicle;
import com.vehicle.Iotproject.repository.FleetRepository;
import com.vehicle.Iotproject.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FleetServices {

    @Autowired
    FleetRepository fleetRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    public List<Fleet> getFleetList() {
        return fleetRepository.findAll();
    }

    public Fleet saveFleet(Fleet fleet) {
        return fleetRepository.save(fleet);
    }

    @Transactional
    public boolean deleteFleetByRoute(String route) {

        Optional<Fleet> fleet = fleetRepository.findByRoute(route);

        if (fleet.isPresent()) {
            updateFleetAssignedVehicle(fleet.get());
            fleetRepository.delete(fleet.get());
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateFleetAssignedVehicle(Fleet fleet) {

        if (fleet.getCount() != 0) {

            List<Vehicle> vehicleList = vehicleRepository.findByFleetFleetId(fleet.getFleetId());

            List<Vehicle> updatedVehicles = vehicleList.stream().map(vehicle -> new Vehicle(vehicle.getVehicleId(), vehicle.getRegistrationNumber(),
                    vehicle.getModel(), vehicle.getStyle(), null)).collect(Collectors.toList());

            vehicleRepository.saveAll(updatedVehicles);
        }
    }
}
