package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Seat;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByFlightIdAndAvailability(int flightId);
}
