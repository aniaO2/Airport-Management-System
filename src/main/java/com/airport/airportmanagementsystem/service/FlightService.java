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
        if(flight.getArrivalTime().isBefore(flight.getDepartureTime())){
            throw new RuntimeException("Arrival time has to be after departure time.");
        }
        if(flight.getDepartureCity().equalsIgnoreCase(flight.getArrivalCity())){
            throw new RuntimeException("Arrival city has to be different from departure city.");
        }
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }
}
