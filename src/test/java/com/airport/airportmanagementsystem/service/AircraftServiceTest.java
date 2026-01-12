package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.repository.AircraftRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest {
    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftService aircraftService;

    @Test
    void saveAircraft_ValidCapacity_Success() {
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(150);
        when(aircraftRepository.save(aircraft)).thenReturn(aircraft);

        Aircraft saved = aircraftService.saveAircraft(aircraft);
        assertNotNull(saved);
        assertEquals(150, saved.getCapacity());
    }

    @Test
    void saveAircraft_InvalidCapacity_ThrowsException() {
        Aircraft aircraft = new Aircraft();
        aircraft.setCapacity(0);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> aircraftService.saveAircraft(aircraft));
        assertEquals("There must be at least one seat in the aircraft.", ex.getMessage());
    }
}