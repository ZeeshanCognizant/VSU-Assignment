package com.vehicle.Iotproject.Service;

import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.dto.DriverResponse;
import com.vehicle.Iotproject.model.Driver;
import com.vehicle.Iotproject.exception.DriverNotFoundException;
import com.vehicle.Iotproject.exception.ErrorCode;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.model.Vehicle;
import com.vehicle.Iotproject.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServices {

    @Autowired
    private DriverRepository driverRepository;

    public Driver saveDriver(DriverRequest driverRequest) {

        Optional<Driver> saveDriver = driverRepository.findByLicenceNumber(driverRequest.getLicenceNumber());

        Driver driver = driverRequestToDriver(driverRequest);
        if (saveDriver.isPresent())
            throw new GeneralException(ErrorCode.DUPLICATE_LICENCE_NUMBER.getReasonPhrase(), ErrorCode.DUPLICATE_LICENCE_NUMBER, HttpStatus.BAD_REQUEST);
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDriver() {
        List<Driver> drivers=driverRepository.findAll();
        if(drivers.isEmpty())
            throw new GeneralException(ErrorCode.DRIVER_LIST_NOT_FOUND.getReasonPhrase(),ErrorCode.DRIVER_LIST_NOT_FOUND,HttpStatus.NOT_FOUND);
        return driverRepository.findAll();
    }

    public Driver getDriverById(int driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver with id " + driverId + " Not Found ", ErrorCode.DRIVER_NOT_FOUND));
    }

    public boolean deleteDriverById(Integer driverId) {
        Optional<Driver> driver = driverRepository.findById(driverId);

        if (driver.isPresent()) {
            driverRepository.deleteById(driverId);
            return true;
        }
        return false;
    }

    public Driver updateDriverById(Driver updatedDriver) {

        Optional<Driver> drivers = driverRepository.findById(updatedDriver.getDriverId());

        if (drivers.isPresent()) {
            return driverRepository.save(updatedDriver);
        }
        throw new DriverNotFoundException("Driver with id " + updatedDriver.getDriverId() + "Not found", ErrorCode.DRIVER_NOT_FOUND);
    }


    public Driver driverRequestToDriver(DriverRequest driverRequest) {

        Driver driver = new Driver();
        driver.setName(driverRequest.getName());
        driver.setAddress(driverRequest.getAddress());
        driver.setLicenceNumber(driverRequest.getLicenceNumber());
        driver.setPhoneNumber(driverRequest.getPhoneNumber());
        return driver;
    }

    public DriverRequest driverToDriverRequest(Driver driver) {

        DriverRequest driverRequest = new DriverRequest();
        driverRequest.setDriverId(driver.getDriverId());
        driverRequest.setName(driver.getName());
        driverRequest.setAddress(driver.getAddress());
        driverRequest.setLicenceNumber(driver.getLicenceNumber());
        driverRequest.setPhoneNumber(driver.getPhoneNumber());
        return driverRequest;
    }

    public DriverResponse driverToDriverResponse(Driver driver) {

        DriverResponse driverResponse = new DriverResponse();
        driverResponse.setDriverId(driver.getDriverId());
        driverResponse.setName(driver.getName());
        driverResponse.setAddress(driver.getAddress());
        driverResponse.setLicenceNumber(driver.getLicenceNumber());
        driverResponse.setPhoneNumber(driver.getPhoneNumber());
        return driverResponse;
    }

}
