package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.service.SeatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatController.class)
public class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeatService seatService;

    @Test
    void getAvailableSeats_ShouldReturnOnlyAvailableSeats() throws Exception {
        Integer flightId = 1;

        Seat availableSeat = new Seat();
        availableSeat.setSeatNo("1A");
        availableSeat.setAvailable(true);

        Seat occupiedSeat = new Seat();
        occupiedSeat.setSeatNo("1B");
        occupiedSeat.setAvailable(false);

        when(seatService.getSeatsByFlight(flightId)).thenReturn(List.of(availableSeat, occupiedSeat));

        mockMvc.perform(get("/api/seats/flight/" + flightId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].seatNo").value("1A"))
                .andExpect(jsonPath("$[0].available").value(true));
    }
}