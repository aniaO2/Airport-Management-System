package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void saveSeat_WhenCapacityNotExceeded_ShouldSaveSuccessfully() {
        String flightNo = "RO101";
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(2);

        Flight flight = new Flight();
        flight.setFlightNo(flightNo);
        flight.setAircraft(aircraft);

        Seat existingSeat = new Seat();
        existingSeat.setFlight(flight);

        Seat newSeat = new Seat();
        newSeat.setFlight(flight);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(seatRepository.findAll()).thenReturn(List.of(existingSeat));
        when(seatRepository.save(newSeat)).thenReturn(newSeat);

        Seat savedSeat = seatService.saveSeat(newSeat);

        assertNotNull(savedSeat);
        verify(seatRepository, times(1)).save(newSeat);
    }

    @Test
    void saveSeat_WhenCapacityExceeded_ShouldThrowException() {
        String flightNo = "RO101";
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(1);

        Flight flight = new Flight();
        flight.setFlightNo(flightNo);
        flight.setAircraft(aircraft);

        Seat existingSeat = new Seat();
        existingSeat.setFlight(flight);

        Seat newSeat = new Seat();
        newSeat.setFlight(flight);

        when(flightRepository.findByFlightNo(flightNo)).thenReturn(Optional.of(flight));
        when(seatRepository.findAll()).thenReturn(List.of(existingSeat));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            seatService.saveSeat(newSeat);
        });

        assertTrue(exception.getMessage().contains("Reached full capacity."));
        verify(seatRepository, never()).save(any(Seat.class));
    }

    @Test
    void getSeatsByFlight_ShouldFilterCorrectly() {
        Integer targetFlightId = 10;
        Flight flight10 = new Flight();
        flight10.setFlightId(targetFlightId);

        Flight flight20 = new Flight();
        flight20.setFlightId(20);

        Seat seat1 = new Seat();
        seat1.setFlight(flight10);
        Seat seat2 = new Seat();
        seat2.setFlight(flight10);
        Seat seat3 = new Seat();
        seat3.setFlight(flight20);

        when(seatRepository.findAll()).thenReturn(List.of(seat1, seat2, seat3));

        List<Seat> result = seatService.getSeatsByFlight(targetFlightId);

        assertEquals(2, result.size());
        verify(seatRepository, times(1)).findAll();
    }
}