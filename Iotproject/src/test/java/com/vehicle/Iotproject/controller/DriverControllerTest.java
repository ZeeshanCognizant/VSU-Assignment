package com.vehicle.Iotproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.Iotproject.Service.DriverServices;
import com.vehicle.Iotproject.dto.DriverRequest;
import com.vehicle.Iotproject.exception.ErrorResponse;
import com.vehicle.Iotproject.model.Driver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest {

    @MockBean
    DriverServices driverServices;

    @Autowired
    MockMvc mockMvc;

    @Test
    void saveDriver() throws Exception {

        DriverRequest driverRequest = DriverRequest.builder().name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();
        Driver driverResponse = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();

        when(driverServices.saveDriver(any(DriverRequest.class))).thenReturn(driverResponse);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(driverRequest)))
                .andExpect(status().isCreated());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        Driver driverResult = new ObjectMapper().readValue(contentAsString, Driver.class);

        assertEquals(driverRequest.getLicenceNumber(), driverResult.getLicenceNumber());

        verify(driverServices, times(1)).saveDriver(any());
    }


    @Test
    void getAllDriver() throws Exception {

        List<Driver> driverList = Arrays.asList(Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build(),
                Driver.builder().driverId(2).name("Kamran").address("Mumbai").licenceNumber("MP14 20186534761").phoneNumber("+919874786543").build());

        when(driverServices.getAllDriver()).thenReturn(driverList);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(2, driverList.size());
    }

    @Test
    void getDriverById() throws Exception {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();

        when(driverServices.getDriverById(1)).thenReturn(driver);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        Driver driverResult = new ObjectMapper().readValue(contentAsString, Driver.class);
        assertEquals(driver.getLicenceNumber(), driverResult.getLicenceNumber());
    }


    @Test
    void deleteDriverById() throws Exception {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();

        when(driverServices.deleteDriverById(1)).thenReturn(true);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/drivers/{id}", 1))
                .andExpect(status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals("Driver with id " + driver.getDriverId() + " Deleted Successfully", contentAsString);
    }

    @Test
    void updateDriverById() throws Exception {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();

        when(driverServices.updateDriverById(driver)).thenReturn(driver);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/drivers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(driver)))
                .andExpect(status().isOk());

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        Driver driverResult = new ObjectMapper().readValue(contentAsString, Driver.class);    //Driver to DriverEntity

        assertEquals(driver.getLicenceNumber(), driverResult.getLicenceNumber());
    }


    @Test
    public void saveDriverReturnsBadRequest() throws Exception {
        DriverRequest driverRequest = DriverRequest.builder().licenceNumber("UP1420160034761").phoneNumber("+919874594650").address("Pune").build();

        when(driverServices.saveDriver(driverRequest)).thenThrow(new IllegalArgumentException());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/driver")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(driverRequest)));

        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        ErrorResponse errorResult = new ObjectMapper().readValue(contentAsString, ErrorResponse.class);
        assertNotSame(" Licence Number should be in this format UP14 20160034761 ", errorResult.getDescription());
    }

    @Test
    public void driverIdNotFound_deleteDriverById_thenReturnEmpty() throws Exception {

        Driver driver = Driver.builder().driverId(1).name("Rajesh").address("Pune").licenceNumber("UP14 20160034761").phoneNumber("+919874594650").build();
        when(driverServices.getDriverById(1)).thenThrow(new RuntimeException());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/api/drivers/{id}", 1));
        resultActions.andExpect(status().isNotFound())
                .andDo(print());
    }


}



