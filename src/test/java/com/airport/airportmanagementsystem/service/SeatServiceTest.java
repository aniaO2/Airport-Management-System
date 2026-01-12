package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private SeatService seatService;

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

        assertEquals(2, result.size(), "Ar trebui să găsească doar cele 2 locuri ale zborului 10");
        assertTrue(result.stream().allMatch(s -> s.getFlight().getFlightId().equals(targetFlightId)));
        verify(seatRepository, times(1)).findAll();
    }
}