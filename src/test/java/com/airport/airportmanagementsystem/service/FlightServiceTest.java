package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.BookingRepository;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void getFlightByNo_Success() {
        Flight flight = new Flight();
        flight.setFlightNo("RO-123");
        when(flightRepository.findByFlightNo("RO-123")).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightByNo("RO-123");

        assertNotNull(result);
        assertEquals("RO-123", result.getFlightNo());
        verify(flightRepository, times(1)).findByFlightNo("RO-123");
    }

    @Test
    void getFlightByNo_NotFound() {
        when(flightRepository.findByFlightNo("RO-404")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            flightService.getFlightByNo("RO-404");
        });

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void deleteFlight_Success() {
        String flightNo = "RO-123";
        Flight flight = new Flight();
        flight.setFlightNo(flightNo);
        Seat occupiedSeat = new Seat();
        occupiedSeat.setAvailable(false);
        Booking booking = new Booking();
        booking.setSeat(occupiedSeat);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(bookingRepository.findByFlight_FlightNo(flightNo)).thenReturn(List.of(booking));

        flightService.deleteFlight(flightNo);

        assertTrue(occupiedSeat.isAvailable());
        verify(seatRepository, times(1)).save(occupiedSeat);
        verify(flightRepository, times(1)).delete(flight);
        verify(bookingRepository, times(1)).deleteAll(anyList());
    }
}