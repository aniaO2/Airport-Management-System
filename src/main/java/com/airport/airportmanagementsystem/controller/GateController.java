package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Gate;
import com.airport.airportmanagementsystem.service.GateService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gates")
public class GateController {
    @Autowired
    private GateService gateService;

    @PostMapping
    public Gate create(@RequestBody Gate gate){
        return gateService.saveGate(gate);
    }

    @GetMapping
    public List<Gate> getAll(){
        return gateService.getAll();
    }
}
