package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void getFlightById_Success(){
        Flight flight = new Flight();
        flight.setFlightId(1);
        flight.setFlightNo("RO-123");
        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightById(1);

        assertNotNull(result);
        assertEquals("RO-123", result.getFlightNo());
    }

    @Test
    void getGetFlightById_NotFound(){
        when(flightRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            flightService.getFlightById(99);
        });
        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void deleteFlight_Success(){
        when(flightRepository.existsById(1)).thenReturn(true);
        flightService.deleteFlight(1);
        verify(flightRepository, times(1)).deleteById(1);
    }
}
