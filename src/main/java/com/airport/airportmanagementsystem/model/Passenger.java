package com.airport.airportmanagementsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "passengers")
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer passengerId;

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "A passport is mandatory")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{7}", message = "Passport format must be of type 'AB1234567'")
    @Column(unique = true)
    private String passportNo;
}
