package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Flights", description="Air travel administration")
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Operation(summary="List of all flights")
    @GetMapping
    public List<Flight> getAll(){
        return flightService.getAllFlights();
    }

    @Operation(summary = "Adds a new flight", description = "This method saves and adds a new flight to the database after validating the constraints.")
    @PostMapping
    public Flight create(@Valid @RequestBody Flight flight){
        return flightService.saveFlight(flight);
    }

    @Operation(summary = "Searches flight by number")
    @GetMapping("/{flightNo}")
    public Flight getByNo(@PathVariable String flightNo){
        return flightService.getFlightByNo(flightNo);
    }

    @Operation(summary = "Deletes flight", description = "This method deletes the flight and its bookings and seats.")
    @DeleteMapping("/{flightNo}")
    public String delete(@PathVariable String flightNo){
        flightService.deleteFlight(flightNo);
        return "Flight with no." + flightNo + " was deleted successfully.";
    }

    @Operation(summary = "Searches flight by destination city")
    @GetMapping("/search")
    public List<Flight> searchByDestination(@RequestParam String city){
        return flightService.findByDestination(city);
    }
}
