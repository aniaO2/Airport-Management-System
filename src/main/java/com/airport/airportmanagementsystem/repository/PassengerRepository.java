package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Passenger;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
}
