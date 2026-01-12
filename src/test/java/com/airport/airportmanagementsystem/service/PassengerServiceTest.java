package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Passenger;
import com.airport.airportmanagementsystem.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void savePassenger_NewPassport_Success() {
        Passenger p = new Passenger();
        p.setPassportNo("RO123");
        when(passengerRepository.existsByPassportNo("RO123")).thenReturn(false);
        when(passengerRepository.save(p)).thenReturn(p);

        Passenger saved = passengerService.savePassenger(p);
        assertNotNull(saved);
    }

    @Test
    void savePassenger_DuplicatePassport_ThrowsException() {
        Passenger p = new Passenger();
        p.setPassportNo("RO123");
        when(passengerRepository.existsByPassportNo("RO123")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> passengerService.savePassenger(p));
    }
}