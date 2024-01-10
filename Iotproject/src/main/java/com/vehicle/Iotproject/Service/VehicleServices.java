package com.vehicle.Iotproject.Service;


import com.vehicle.Iotproject.dto.VehicleRequest;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.model.Fleet;
import com.vehicle.Iotproject.model.Vehicle;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.exception.VehicleNotFoundException;
import com.vehicle.Iotproject.repository.FleetRepository;
import com.vehicle.Iotproject.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
public class VehicleServices {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    FleetRepository fleetRepository;

    @Transactional
    public Vehicle saveVehicle(VehicleRequest vehicleRequest) {

        Optional<Vehicle> saveVehicle = vehicleRepository.findByRegistrationNumber(vehicleRequest.getRegistrationNumber());

        if (saveVehicle.isPresent())
            throw new GeneralException(ErrorCode.DUPLICATE_REGISTRATION_NUMBER.getReasonPhrase(), ErrorCode.DUPLICATE_REGISTRATION_NUMBER, HttpStatus.BAD_REQUEST);
        Vehicle vehicle = vehicleRequestToVehicle(vehicleRequest);

        Fleet fleet = vehicle.getFleet();
        String route = fleet.getRoute();

        Optional<Fleet> fleetByRoute = fleetRepository.findByRoute(route);

        if (fleetByRoute.isEmpty()) {
            fleet.setCount(1);
            Fleet fleet1 = fleetRepository.save(fleet);
            vehicle.setFleet(fleet1);
        } else {
            fleetByRoute.get().setCount(fleetByRoute.get().getCount() + 1);
            Fleet upatedFleet = fleetRepository.saveAndFlush(fleetByRoute.get());
            vehicle.setFleet(upatedFleet);
        }
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicle() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles.isEmpty())
            throw new GeneralException(ErrorCode.VEHICLE_LIST_NOT_FOUND.getReasonPhrase(), ErrorCode.VEHICLE_LIST_NOT_FOUND, HttpStatus.NOT_FOUND);
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(int vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("vehicle not found for id " + vehicleId, ErrorCode.VEHICLE_NOT_FOUND));
    }

    @Transactional
    public boolean deleteVehicleById(int vehicleId) {

        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);

        if (vehicle.isPresent()) {
            int fleetId = vehicle.get().getFleet().getFleetId();
            decreaseCountOfFleet(fleetId);
            vehicleRepository.deleteById(vehicleId);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void decreaseCountOfFleet(int fleetId) {
        Optional<Fleet> fleet = fleetRepository.findById(fleetId);

        fleet.get().setCount(fleet.get().getCount() - 1);
        fleetRepository.save(fleet.get());
    }


    private Vehicle vehicleRequestToVehicle(VehicleRequest vehicleRequest) {

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(vehicleRequest.getRegistrationNumber());
        vehicle.setModel(vehicleRequest.getModel());
        vehicle.setStyle(vehicleRequest.getStyle());
        vehicle.setFleet(new Fleet(0, 0, vehicleRequest.getRoute()));

        return vehicle;
    }


}
