package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> getSeatsByFlight(Integer flightId){
        return seatRepository.findAll().stream()
                .filter(seat -> seat.getFlight().getFlightId().equals(flightId))
                .toList();
    }
}
