package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findByFlightNo(String flightNo);
}
