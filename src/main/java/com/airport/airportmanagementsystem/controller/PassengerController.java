package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.repository.PassengerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping
    public List<Passenger> getAll(){
        return passengerRepository.findAll();
    }

    @PostMapping
    public Passenger create(@Valid @RequestBody Passenger passenger){
        return passengerRepository.save(passenger);
    }
}
