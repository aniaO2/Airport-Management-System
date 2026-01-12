package com.airport.airportmanagementsystem.controller;

import com.airport.airportmanagementsystem.dto.BookingResponseDTO;
import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void create_ShouldReturnBookingDTO() throws Exception {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setBookingNo("TX12345");
        dto.setFlightNo("RO101");
        dto.setPassengerName("Andrei Ionescu");
        dto.setSeatNo("12B");
        dto.setDepartureTime(LocalDateTime.now().plusDays(1));

        when(bookingService.createBooking("RO101", "AB123")).thenReturn(dto);

        mockMvc.perform(post("/api/bookings")
                        .param("flightNo", "RO101")
                        .param("passportNo", "AB123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingNo").value("TX12345"))
                .andExpect(jsonPath("$.passengerName").value("Andrei Ionescu"));
    }



    @Test
    void getPassengerHistory_ShouldReturnList() throws Exception {
        Booking booking = new Booking();
        booking.setBookingNo("B001");
        when(bookingService.getBookingsByPassenger("AB123")).thenReturn(List.of(booking));

        mockMvc.perform(get("/api/bookings/passenger/AB123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bookingNo").value("B001"));
    }

    @Test
    void cancel_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/bookings/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking has been cancelled."));
    }
}