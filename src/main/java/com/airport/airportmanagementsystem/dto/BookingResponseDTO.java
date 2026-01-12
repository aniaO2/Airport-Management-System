package com.airport.airportmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private String bookingNo;
    private String flightNo;
    private String passengerName;
    private String seatNo;
    private LocalDateTime departureTime;
}
