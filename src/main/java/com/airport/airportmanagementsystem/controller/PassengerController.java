package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Passengers", description = "Manages passenger data")
@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @Operation(summary = "Returns all passengers")
    @GetMapping
    public List<Passenger> getAll(){
        return passengerService.getAllPassengers();
    }

    @Operation(summary="Register new passenger", description = "This method saves a new passenger to the database after validations.")
    @PostMapping
    public Passenger create(@Valid @RequestBody Passenger passenger){
        return passengerService.savePassenger(passenger);
    }
}
