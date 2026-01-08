package com.airport.airportmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "aicrafts")
@Data
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @Min(value = 1, message = "Capacity needs to be at least 1")
    private int capacity;
}
