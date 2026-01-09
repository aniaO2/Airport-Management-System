package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping
    public List<Flight> getAll(){
        return flightService.getAllFlights();
    }

    @PostMapping
    public Flight create(@Valid @RequestBody Flight flight){
        return flightService.saveFlight(flight);
    }
}
