package com.airport.airportmanagementsystem.repository;

import com.airport.airportmanagementsystem.model.Booking;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByPassenger_PassportNo(String passportNo);
    List<Booking> findByFlight_FlightNo(String flightNo);
    boolean existsByBookingNo(String bookingNo);
}
