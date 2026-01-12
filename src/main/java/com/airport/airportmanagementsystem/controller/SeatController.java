package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Seats", description = "Flight seats availability management")
@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @Operation(summary = "Adds a new seat", description = "Adds a new seat if maximum capacity hasn't been reached.")
    @PostMapping
    public Seat create(@RequestBody Seat seat) {
        return seatService.saveSeat(seat);
    }

    @Operation(summary = "Returns all available seats", description = "This method returns all the available seats for a specific flight.")
    @GetMapping("/flight/{flightId}")
    public List<Seat> getAvailableSeats(@PathVariable Integer flightId) {
        return seatService.getSeatsByFlight(flightId).stream()
                .filter(Seat::isAvailable)
                .toList();
    }
}
