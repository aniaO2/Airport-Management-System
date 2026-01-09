package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight saveFlight(Flight flight){
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }
}
