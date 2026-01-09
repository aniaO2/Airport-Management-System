package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Aircraft;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {}
