package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.service.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Aircrafts", description = "Air fleet management")
@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {
    @Autowired
    private AircraftService aircraftService;

    @Operation(summary = "Register aircraft", description = "Adds a plane to the database.")
    @PostMapping
    public Aircraft create(@RequestBody Aircraft aircraft){
        return aircraftService.saveAircraft(aircraft);
    }

    @Operation(summary = "Returns all registered aircrafts")
    @GetMapping
    public List<Aircraft> getAll(){
        return aircraftService.getAll();
    }
}
