package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking create(@RequestParam int flightId, @RequestParam String passportNo){
        return bookingService.createBooking(flightId, passportNo);
    }
}
