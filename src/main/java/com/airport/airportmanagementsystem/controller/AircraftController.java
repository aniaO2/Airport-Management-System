package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.service.AircraftService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircrafts")
public class AircraftController {
    @Autowired
    private AircraftService aircraftService;

    @PostMapping
    public Aircraft create(@RequestBody Aircraft aircraft){
        return aircraftService.saveAircraft(aircraft);
    }

    @GetMapping
    public List<Aircraft> getAll(){
        return aircraftService.getAll();
    }
}
