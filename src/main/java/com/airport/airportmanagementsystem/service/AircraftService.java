package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Aircraft;
import com.airport.airportmanagementsystem.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AircraftService {
    @Autowired
    private AircraftRepository aircraftRepository;

    public Aircraft saveAircraft(Aircraft aircraft){
        if(aircraft.getCapacity() <= 0){
            throw new RuntimeException("There must be at least one seat in the aircraft.");
        }
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> getAll(){
        return aircraftRepository.findAll();
    }
}
