package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Gate;
import com.airport.airportmanagementsystem.repository.GateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GateServiceTest {

    @Mock
    private GateRepository gateRepository;

    @InjectMocks
    private GateService gateService;

    @Test
    void saveGate_Success() {
        Gate gate = new Gate();
        gate.setGateNo("A1");
        when(gateRepository.save(any(Gate.class))).thenReturn(gate);

        Gate result = gateService.saveGate(new Gate());

        assertNotNull(result);
        assertEquals("A1", result.getGateNo());
        verify(gateRepository, times(1)).save(any(Gate.class));
    }

    @Test
    void getAll_ShouldReturnList() {
        when(gateRepository.findAll()).thenReturn(List.of(new Gate(), new Gate()));

        List<Gate> result = gateService.getAll();

        assertEquals(2, result.size());
        verify(gateRepository, times(1)).findAll();
    }
}