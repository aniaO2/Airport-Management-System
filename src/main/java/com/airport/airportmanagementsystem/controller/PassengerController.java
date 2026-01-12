package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public List<Passenger> getAll(){
        return passengerService.getAllPassengers();
    }

    @PostMapping
    public Passenger create(@Valid @RequestBody Passenger passenger){
        return passengerService.savePassenger(passenger);
    }
}
