package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.repository.GateRepository;
import com.airport.airportmanagementsystem.model.Gate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService {
    @Autowired
    private GateRepository gateRepository;

    public Gate saveGate(Gate gate){
        return gateRepository.save(gate);
    }

    public List<Gate> getAll(){
        return gateRepository.findAll();
    }
}
