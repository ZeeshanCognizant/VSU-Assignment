package com.vehicle.Iotproject.Service;

import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.exception.DriverNotFoundException;
import com.vehicle.Iotproject.exception.GeneralException;
import com.vehicle.Iotproject.model.Driver;
import com.vehicle.Iotproject.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class DriverServicesTest {

    @MockBean
    DriverRepository driverRepository;

    @Autowired
    DriverServices driverServices;

    @Test
    void testSaveDriver() {

        DriverRequest driverRequest = DriverRequest.builder().name("Rajesh").address("Pune").licenceNumber("MH86386_test").phoneNumber("9874594650").build();

        Driver driver = driverServices.driverRequestToDriver(driverRequest);
        when(driverRepository.save(driver)).thenReturn(driver);
        assertThat(driverRequest).isNotNull();
        assertEquals(driver.getAddress(), driverServices.saveDriver(driverRequest).getAddress());
        assertEquals(driver.getLicenceNumber(), driverServices.saveDriver(driverRequest).getLicenceNumber());
    }


    @Test
    void testGetAllDriver() {

        List<Driver> driverList = Arrays.asList(Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("MH86386").phoneNumber("9874594650").build(),
                Driver.builder().driverId(2).name("Kamran").address("Mumbai").licenceNumber("MH352667").phoneNumber("9563286466").build());

        when(driverRepository.findAll()).thenReturn(driverList);
        assertEquals(driverList.size(), driverServices.getAllDriver().size());
    }

    @Test
    void testGetDriverById() {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("MH86386").phoneNumber("9874594650").build();

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        assertEquals(driver.getLicenceNumber(), driverServices.getDriverById(1).getLicenceNumber());
    }


    @Test
    void testDeleteDriverById() {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("MH86386").phoneNumber("9874594650").build();

        when(driverRepository.findById(driver.getDriverId())).thenReturn(Optional.of(driver));
        assertTrue(driverServices.deleteDriverById(driver.getDriverId()));
    }


    @Test
    void testUpdateDriverById() {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("MH86386").phoneNumber("9874594650").build();

        when(driverRepository.findById(driver.getDriverId())).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);
        Driver updatedDriver = driverServices.updateDriverById(driver);
        assertThat(updatedDriver).isNotNull();
    }


    @Test
    @ExceptionHandler(GeneralException.class)
    public void testSaveDriver_duplicateLicenceNumber() {
        DriverRequest driverRequest = new DriverRequest();
        driverRequest.setLicenceNumber("123456");

        Driver driver = driverServices.driverRequestToDriver(driverRequest);
        when(driverRepository.findByLicenceNumber(driverRequest.getLicenceNumber())).thenReturn(Optional.of(driver));
        assertThrows(GeneralException.class, () -> {
            driverServices.saveDriver(driverRequest);
        });
    }

    @Test
    public void testGetAllDriver_noDrivers() {

        when(driverRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(GeneralException.class, () -> {
            driverServices.getAllDriver();
        });
    }

    @Test
    @ExceptionHandler(DriverNotFoundException.class)
    public void testDeleteDriverByIdException() {

        int driverId = 2; // assuming 2 is an ID that does not exist.

        when(driverRepository.findById(driverId)).thenReturn(empty());
        boolean result = driverServices.deleteDriverById(driverId);
        assertFalse(result);   // Verify the result
    }


    @Test
    public void testUpdateDriverById_driverNotFound() {
        Driver updatedDriver = new Driver();
        updatedDriver.setDriverId(3);

        when(driverRepository.findById(updatedDriver.getDriverId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(DriverNotFoundException.class, () -> {
            driverServices.updateDriverById(updatedDriver);
        });

        String expectedMessage = "Driver with id " + updatedDriver.getDriverId() + "Not found";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

}