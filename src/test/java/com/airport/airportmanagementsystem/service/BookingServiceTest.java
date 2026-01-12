package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.dto.BookingResponseDTO;
import com.airport.airportmanagementsystem.model.*;
import com.airport.airportmanagementsystem.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void createBooking_Success() {
        String flightNo = "RO101";
        String passport = "AB1234567";

        Flight flight = new Flight();
        flight.setFlightNo(flightNo);
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));

        Passenger passenger = new Passenger();
        passenger.setFullName("Andrei Ionescu");
        passenger.setPassportNo(passport);

        Seat seat = new Seat();
        seat.setSeatNo("1A");
        seat.setAvailable(true);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(passengerRepository.findByPassportNo(passport)).thenReturn(Optional.of(passenger));
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>()); // Nu are rezervări
        when(seatRepository.findByFlight_FlightNoAndIsAvailableTrue(flightNo)).thenReturn(List.of(seat));
        when(bookingRepository.existsByBookingNo(any())).thenReturn(false);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        BookingResponseDTO result = bookingService.createBooking(flightNo, passport);

        assertNotNull(result);
        assertEquals(flightNo, result.getFlightNo());
        assertEquals("Andrei Ionescu", result.getPassengerName());
        assertFalse(seat.isAvailable()); // Verificăm că locul a fost ocupat
        verify(seatRepository, times(1)).save(seat);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }



    @Test
    void createBooking_AlreadyBooked_ThrowsException() {
        String flightNo = "RO101";
        String passport = "AB1234567";

        Flight flight = new Flight();
        flight.setFlightNo(flightNo);
        Passenger passenger = new Passenger();
        passenger.setPassportNo(passport);

        Booking existingBooking = new Booking();
        existingBooking.setFlight(flight);
        existingBooking.setPassenger(passenger);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(passengerRepository.findByPassportNo(passport)).thenReturn(Optional.of(passenger));
        when(bookingRepository.findAll()).thenReturn(List.of(existingBooking));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookingService.createBooking(flightNo, passport)
        );

        assertEquals("Passenger already booked this flight.", exception.getMessage());
    }

    @Test
    void createBooking_FlightFull_ThrowsException() {
        when(flightRepository.findByFlightNo(any())).thenReturn(Optional.of(new Flight()));
        when(passengerRepository.findByPassportNo(any())).thenReturn(Optional.of(new Passenger()));
        when(bookingRepository.findAll()).thenReturn(new ArrayList<>());
        when(seatRepository.findByFlight_FlightNoAndIsAvailableTrue(any())).thenReturn(List.of()); // Niciun loc

        assertThrows(RuntimeException.class, () -> bookingService.createBooking("RO1", "AB1"));
    }

    @Test
    void cancelBooking_Success() {
        Integer bookingId = 1;
        Seat seat = new Seat();
        seat.setAvailable(false);

        Booking booking = new Booking();
        booking.setSeat(seat);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingId);

        assertTrue(seat.isAvailable()); // Locul trebuie să redevină liber
        verify(seatRepository).save(seat);
        verify(bookingRepository).delete(booking);
    }
}