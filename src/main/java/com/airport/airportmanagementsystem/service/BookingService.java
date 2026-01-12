package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.dto.BookingResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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

    public BookingResponseDTO createBooking(String flightNo, String passportNo){
        Flight flight = flightRepository.findByFlightNo(flightNo)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Passenger passenger = passengerRepository.findByPassportNo(passportNo)
                .orElseThrow(() -> new RuntimeException("Passenger not found"));

        boolean alreadyBooked = bookingRepository.findAll().stream()
                .anyMatch(b -> b.getFlight().getFlightNo().equals(flightNo)
                            && b.getPassenger().getPassportNo().equals(passportNo));
        if(alreadyBooked){
            throw new RuntimeException("Passenger already booked this flight.");
        }

        Seat availableSeat = seatRepository.findByFlight_FlightNoAndIsAvailableTrue(flightNo)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("The flight is fully booked"));


        availableSeat.setAvailable(false);
        seatRepository.save(availableSeat);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setPassenger(passenger);
        booking.setSeat(availableSeat);
        booking.setBookingDate(LocalDateTime.now());

        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (bookingRepository.existsByBookingNo(code));
        booking.setBookingNo(code);
        Booking savedBooking = bookingRepository.save(booking);
        return convertToDTO(savedBooking);
    }

    public List<Booking> getBookingsByPassenger(String passportNo){
        return bookingRepository.findByPassenger_PassportNo(passportNo);
    }

    @Transactional
    public void cancelBooking(Integer bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if(booking.getSeat() != null){
            Seat seat = booking.getSeat();
            seat.setAvailable(true);
            seatRepository.save(seat);
        }
        bookingRepository.delete(booking);
    }
    private BookingResponseDTO convertToDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingNo(booking.getBookingNo());
        dto.setFlightNo(booking.getFlight().getFlightNo());
        dto.setPassengerName(booking.getPassenger().getFullName());
        dto.setSeatNo(booking.getSeat().getSeatNo());
        dto.setDepartureTime(booking.getFlight().getDepartureTime());
        return dto;
    }
}
