package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.service.PassengerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerController.class)
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PassengerService passengerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnSavedPassenger() throws Exception {
        Passenger passenger = new Passenger();
        passenger.setPassengerId(1);
        passenger.setFullName("Maria Popescu");
        passenger.setPassportNo("RO9876542");

        when(passengerService.savePassenger(any(Passenger.class))).thenReturn(passenger);

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passenger)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Maria Popescu"))
                .andExpect(jsonPath("$.passportNo").value("RO9876542"));
    }



    @Test
    void getAll_ShouldReturnList() throws Exception {
        Passenger p1 = new Passenger();
        p1.setFullName("Ion Ionescu");
        when(passengerService.getAllPassengers()).thenReturn(List.of(p1));

        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].fullName").value("Ion Ionescu"));
    }
}