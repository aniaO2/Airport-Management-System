package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Gate;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface GateRepository extends JpaRepository<Gate, Integer> {
}
