package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private FlightRepository flightRepository;

    public Seat saveSeat(Seat seat){
        Flight flight = flightRepository.findByFlightNo(seat.getFlight().getFlightNo())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        int maxCapacity = flight.getAircraft().getCapacity();
        long existingSeats = seatRepository.findAll().stream()
                .filter(s -> s.getFlight().getFlightNo().equals(flight.getFlightNo()))
                .count();
        if(existingSeats >= maxCapacity){
            throw new RuntimeException("Reached full capacity.");
        }
        return seatRepository.save(seat);
    }
    public List<Seat> getSeatsByFlight(Integer flightId){
        return seatRepository.findAll().stream()
                .filter(seat -> seat.getFlight().getFlightId().equals(flightId))
                .toList();
    }
}
