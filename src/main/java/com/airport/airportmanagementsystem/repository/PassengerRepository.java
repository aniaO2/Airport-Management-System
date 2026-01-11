package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByPassportNo(String passportNo);
    boolean existsByPassportNo(String passportNo);
}
