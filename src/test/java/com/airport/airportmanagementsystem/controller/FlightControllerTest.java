package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_ShouldReturnList() throws Exception {
        when(flightService.getAllFlights()).thenReturn(List.of(new Flight()));

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getByNo_ShouldReturnFlight() throws Exception {
        Flight f = new Flight();
        f.setFlightNo("RO101");
        when(flightService.getFlightByNo("RO101")).thenReturn(f);

        mockMvc.perform(get("/api/flights/RO101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flightNo").value("RO101"));
    }

    @Test
    void delete_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/flights/RO101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight with no.RO101 was deleted successfully."));
    }
}