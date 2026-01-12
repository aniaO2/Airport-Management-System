package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.dto.BookingResponseDTO;
import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public BookingResponseDTO create(@RequestParam String flightNo, @RequestParam String passportNo){
        return bookingService.createBooking(flightNo, passportNo);
    }

    @GetMapping("/passenger/{passportNo}")
    public List<Booking> getPassengerHistory(@PathVariable String passportNo){
        return bookingService.getBookingsByPassenger(passportNo);
    }

    @DeleteMapping("/cancel/{id}")
    public String cancel(@PathVariable Integer id){
        bookingService.cancelBooking(id);
        return "Booking has been cancelled.";
    }
}
