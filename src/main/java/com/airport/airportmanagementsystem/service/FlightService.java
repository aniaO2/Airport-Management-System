package com.airport.airportmanagementsystem.service;

import com.airport.airportmanagementsystem.model.Booking;
import com.airport.airportmanagementsystem.model.Flight;
import com.airport.airportmanagementsystem.model.Gate;
import com.airport.airportmanagementsystem.model.Seat;
import com.airport.airportmanagementsystem.repository.BookingRepository;
import com.airport.airportmanagementsystem.repository.FlightRepository;
import com.airport.airportmanagementsystem.repository.GateRepository;
import com.airport.airportmanagementsystem.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Flight saveFlight(Flight flight){
        if(flight.getArrivalTime().isBefore(flight.getDepartureTime())){
            throw new RuntimeException("Arrival time has to be after departure time.");
        }
        if(flight.getDepartureCity().equalsIgnoreCase(flight.getArrivalCity())){
            throw new RuntimeException("Arrival city has to be different from departure city.");
        }
        validateGateAllocation(flight);
        if(flight.getGate() == null) {
            Gate autoGate = findAvailableGate(flight.getDepartureTime());
            flight.setGate(autoGate);
        }
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights(){
        return flightRepository.findAll();
    }

    public Flight getFlightByNo(String flightNo){
        return flightRepository.findByFlightNo(flightNo)
                .orElseThrow(() -> new RuntimeException("Flight with no. " + flightNo + " not found"));
    }

    @Transactional
    public void deleteFlight(String flightNo) {
        Flight flight = flightRepository.findByFlightNo(flightNo)
                .orElseThrow(() -> new RuntimeException("Flight not found."));

        List<Booking> flightBookings = bookingRepository.findByFlight_FlightNo(flightNo);
        bookingRepository.deleteAll(flightBookings);

        List<Seat> flightSeats = seatRepository.findByFlight_FlightNo(flightNo);
        seatRepository.deleteAll(flightSeats);

        bookingRepository.flush();
        seatRepository.flush();

        flightRepository.delete(flight);
    }

    public List<Flight> findByDestination(String city){
        return flightRepository.findAll().stream()
                .filter(f -> f.getArrivalCity().equalsIgnoreCase(city))
                .toList();
    }

    public void validateGateAllocation(Flight flight){
        LocalDateTime now = LocalDateTime.now();
        long minutesUntilDeparture = java.time.Duration.between(now, flight.getDepartureTime()).toMinutes();
        if(minutesUntilDeparture <= 40 && flight.getGate() == null) {
            throw new RuntimeException("Gate allocation is mandatory 40 minutes before flight.");
        }
    }

    public Gate findAvailableGate(LocalDateTime departureTime){
        List<Gate> allGates = gateRepository.findAll();
        List<Flight> allFlights = flightRepository.findAll();

        for(Gate gate : allGates){
            boolean isOccupied = allFlights.stream()
                    .filter(f -> f.getGate() != null && f.getGate().getGateId().equals(gate.getGateId()))
                    .anyMatch(f -> {
                        LocalDateTime gateOccupiedStart = f.getDepartureTime().minusMinutes(60);
                        LocalDateTime gateOccupiedEnd = f.getDepartureTime().plusMinutes(20);

                        return departureTime.isAfter(gateOccupiedStart) && departureTime.isBefore(gateOccupiedEnd);
                    });
            if(!isOccupied){
                return gate;
            }
        }
        throw new RuntimeException("No available gates.");
    }
}
