package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.BookingRepository;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.PassengerRepository;
import com.airport.airportmanagementsystem.repository.SeatRepository;
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

    @Autowired
    private SeatRepository seatRepository;

    public Booking createBooking(Integer flightId, String passportNo){
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Seat availableSeat = seatRepository.findByFlightIdAndAvailability(flightId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("The flight is fully booked"));

        Passenger passenger = passengerRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        availableSeat.setAvailable(false);
        seatRepository.save(availableSeat);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingNo(code));
        booking.setBookingNo(code);
        return bookingRepository.save(booking);
    }
}
