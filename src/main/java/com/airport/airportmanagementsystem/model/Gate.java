package com.airport.airportmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "gates")
@Data
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gateId;

    @NotBlank(message = "Gate number is mandatory")
    private String gateNo;

    private String terminal;
}
