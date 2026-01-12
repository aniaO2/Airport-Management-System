package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Gate;
import com.airport.airportmanagementsystem.service.GateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Gates", description = "Boarding gates management")
@RestController
@RequestMapping("/api/gates")
public class GateController {
    @Autowired
    private GateService gateService;

    @Operation(summary = "Adds a new gate", description = "Creates a new boarding gate.")
    @PostMapping
    public Gate create(@RequestBody Gate gate){
        return gateService.saveGate(gate);
    }

    @Operation(summary = "Returns all boarding gates")
    @GetMapping
    public List<Gate> getAll(){
        return gateService.getAll();
    }
}
