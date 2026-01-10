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

    public void occupySeat(Integer seatId){
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        if(!seat.isAvailable()){
            throw new RuntimeException("Seat is already booked.");
        }
        seat.setAvailable(false);
        seatRepository.save(seat);
    }
}
