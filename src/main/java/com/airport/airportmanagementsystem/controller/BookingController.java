package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.dto.BookingResponseDTO;
import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bookings", description = "Manages bookings and passenger booking history.")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Creates a reservation", description = "This method creates a booking for a passenger for a selected flight.")
    @PostMapping
    public BookingResponseDTO create(@RequestParam String flightNo, @RequestParam String passportNo){
        return bookingService.createBooking(flightNo, passportNo);
    }

    @Operation(summary="Booking history", description = "Returns every booking the specific passenger made based on their passport number.")
    @GetMapping("/passenger/{passportNo}")
    public List<Booking> getPassengerHistory(@PathVariable String passportNo){
        return bookingService.getBookingsByPassenger(passportNo);
    }

    @Operation(summary="Cancel booking", description = "This method deletes a booking from the databases and updates the data.")
    @DeleteMapping("/cancel/{id}")
    public String cancel(@PathVariable Integer id){
        bookingService.cancelBooking(id);
        return "Booking has been cancelled.";
    }
}
