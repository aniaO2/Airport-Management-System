package com.airport.airportmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Seat number is mandatory")
    private String seatNo;

    private String type;
    private boolean isAvailable = true;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
