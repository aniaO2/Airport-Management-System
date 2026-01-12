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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void getAllFlights_Success() {
        when(flightRepository.findAll()).thenReturn(List.of(new Flight(), new Flight()));
        List<Flight> result = flightService.getAllFlights();
        assertEquals(2, result.size());
    }

    @Test
    void getFlightByNo_Success() {
        Flight flight = new Flight();
        flight.setFlightNo("RO101");
        when(flightRepository.findByFlightNo("RO101")).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightByNo("RO101");
        assertEquals("RO101", result.getFlightNo());
    }

    @Test
    void saveFlight_TimeValidation_ThrowsException() {
        Flight flight = new Flight();
        flight.setDepartureTime(LocalDateTime.now().plusHours(2));
        flight.setArrivalTime(LocalDateTime.now().plusHours(1));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> flightService.saveFlight(flight));
        assertEquals("Arrival time has to be after departure time.", ex.getMessage());
    }

    @Test
    void deleteFlight_Success() {
        String flightNo = "RO101";
        Flight flight = new Flight();
        flight.setFlightNo(flightNo);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(bookingRepository.findByFlight_FlightNo(flightNo)).thenReturn(List.of(new Booking()));
        when(seatRepository.findByFlight_FlightNo(flightNo)).thenReturn(List.of(new Seat()));

        flightService.deleteFlight(flightNo);

        verify(bookingRepository).deleteAll(anyList());
        verify(seatRepository).deleteAll(anyList());
        verify(flightRepository).delete(flight);
    }
}