package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/flight/{flightId}")
    public List<Seat> getAvailableSeats(@PathVariable Integer flightId) {
        return seatService.getSeatsByFlight(flightId).stream()
                .filter(Seat::isAvailable)
                .toList();
    }
}
