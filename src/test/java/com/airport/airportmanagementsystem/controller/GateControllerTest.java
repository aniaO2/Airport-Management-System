package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Gate;
import com.airport.airportmanagementsystem.service.GateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GateController.class)
public class GateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GateService gateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnSavedGate() throws Exception {
        Gate gate = new Gate();
        gate.setGateId(1);
        gate.setGateNo("A12");

        when(gateService.saveGate(any(Gate.class))).thenReturn(gate);

        mockMvc.perform(post("/api/gates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gateNo").value("A12"))
                .andExpect(jsonPath("$.gateId").value(1));
    }



    @Test
    void getAll_ShouldReturnList() throws Exception {
        Gate g1 = new Gate();
        g1.setGateNo("B01");
        when(gateService.getAll()).thenReturn(List.of(g1));

        mockMvc.perform(get("/api/gates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].gateNo").value("B01"));
    }
}