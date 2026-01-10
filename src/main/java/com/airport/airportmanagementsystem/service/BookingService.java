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

    public Booking createBooking(Integer flightId, Integer passengerId){
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        long currentBookings = bookingRepository.findAll().stream()
                .filter(b -> b.getFlight().getFlightId().equals(flightId)).count();
        if(currentBookings >= flight.getAircraft().getCapacity()){
            throw new RuntimeException("The aircraft is fully booked.");
        }
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setBookingNo(UUID.randomUUID().toString());
        return bookingRepository.save(booking);
    }
}
