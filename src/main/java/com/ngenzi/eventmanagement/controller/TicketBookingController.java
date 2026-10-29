package com.ngenzi.eventmanagement.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.entity.TicketBooking;
import com.ngenzi.eventmanagement.service.TicketBookingService;

@RestController
@RequestMapping("/api/ticket-bookings")
public class TicketBookingController {

    @Autowired
    private TicketBookingService ticketBookingService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTicketBooking(@RequestBody TicketBooking ticketBooking) {
        return new ResponseEntity<>(ticketBookingService.saveTicketBooking(ticketBooking), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketBooking>> getAllTicketBookings() {
        return new ResponseEntity<>(ticketBookingService.getAllTicketBookings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketBookingById(@PathVariable UUID id) {
        return ticketBookingService.getTicketBookingById(id)
                .<ResponseEntity<?>>map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The ticket booking with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTicketBooking(@PathVariable UUID id, @RequestBody TicketBooking ticketBooking) {
        String message = ticketBookingService.updateTicketBooking(id, ticketBooking);
        if (message.equals("Ticket booking updated successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTicketBooking(@PathVariable UUID id) {
        String message = ticketBookingService.deleteTicketBooking(id);
        if (message.equals("Ticket booking deleted successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<TicketBooking>> getTicketBookingsByStatus(@RequestParam BookingStatus status) {
        return new ResponseEntity<>(ticketBookingService.getTicketBookingsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<TicketBooking>> getTicketBookingsBetween(@RequestParam String start, @RequestParam String end) {
        return new ResponseEntity<>(ticketBookingService.getTicketBookingsBetween(LocalDate.parse(start), LocalDate.parse(end)), HttpStatus.OK);
    }
}
