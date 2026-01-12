package com.airport.airportmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @NotBlank(message = "The flight no. is mandatory")
    @Column(unique = true)
    private String flightNo;

    @Future(message = "Departure time should be in the future")
    private LocalDateTime departureTime;

    @Future(message = "Arrival time should be in the future")
    private LocalDateTime arrivalTime;

    @NotBlank(message = "Departure city is mandatory")
    private String departureCity;

    @NotBlank(message = "Arrival city is mandatory")
    private String arrivalCity;

    @ManyToOne
    @JoinColumn(name = "aircraft_no", nullable = false, referencedColumnName = "planeNo")
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "gate_no", referencedColumnName = "gateNo")
    private Gate gate;
}
