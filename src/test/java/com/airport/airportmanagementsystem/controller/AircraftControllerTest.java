package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.service.AircraftService;
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

@WebMvcTest(AircraftController.class)
public class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AircraftService aircraftService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnSavedAircraft() throws Exception {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftId(1);
        aircraft.setModel("Boeing 747");
        aircraft.setCapacity(400);

        when(aircraftService.saveAircraft(any(Aircraft.class))).thenReturn(aircraft);

        mockMvc.perform(post("/api/aircrafts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aircraft)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Boeing 747"))
                .andExpect(jsonPath("$.capacity").value(400));
    }



    @Test
    void getAll_ShouldReturnList() throws Exception {
        Aircraft a1 = new Aircraft();
        a1.setModel("Airbus A320");
        when(aircraftService.getAll()).thenReturn(List.of(a1));

        mockMvc.perform(get("/api/aircrafts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].model").value("Airbus A320"));
    }
}