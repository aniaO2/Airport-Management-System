package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.service.SeatService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeatController.class)
public class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeatService seatService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnSavedSeat() throws Exception {
        Seat seat = new Seat();
        seat.setSeatNo("12C");
        seat.setAvailable(true);

        when(seatService.saveSeat(any(Seat.class))).thenReturn(seat);

        mockMvc.perform(post("/api/seats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatNo").value("12C"))
                .andExpect(jsonPath("$.available").value(true));
    }

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