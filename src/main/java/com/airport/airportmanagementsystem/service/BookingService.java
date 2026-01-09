package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.repository.BookingRepository;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    public Booking createBooking(int flightId, int passengerId){
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setBookingNo(UUID.randomUUID().toString());
        return bookingRepository.save(booking);
    }
}
