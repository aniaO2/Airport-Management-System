package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Flight getFlightById(Integer id){
        return flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight with id " + id + " not found"));
    }

    public void deleteFlight(Integer id){
        if(!flightRepository.existsById(id)){
            throw new RuntimeException("Flight not found, cannot delete.");
        }
        flightRepository.deleteById(id);
    }

    public List<Flight> findByDestination(String city){
        return flightRepository.findAll().stream()
                .filter(f -> f.getArrivalCity().equalsIgnoreCase(city))
                .toList();
    }

    public void validateGateAllocation(Flight flight){
        LocalDateTime now = LocalDateTime.now();
        long minutesUntilDeparture = java.time.Duration.between(now, flight.getDepartureTime()).toMinutes();
        if(minutesUntilDeparture <= 40 && flight.getGate() == null) {
            throw new RuntimeException("Gate allocation is mandatory 40 minutes before flight.");
        }
    }
}
