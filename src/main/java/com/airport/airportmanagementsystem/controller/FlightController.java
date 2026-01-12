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

    @GetMapping("/{flightNo}")
    public Flight getByNo(@PathVariable String flightNo){
        return flightService.getFlightByNo(flightNo);
    }

    @DeleteMapping("/{flightNo}")
    public String delete(@PathVariable String flightNo){
        flightService.deleteFlight(flightNo);
        return "Flight with no." + flightNo + "was deleted successfully.";
    }

    @GetMapping("/search")
    public List<Flight> searchByDestination(@RequestParam String city){
        return flightService.findByDestination(city);
    }
}
