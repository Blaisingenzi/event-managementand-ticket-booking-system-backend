package com.ngenzi.eventmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngenzi.eventmanagement.entity.Ticket;
import com.ngenzi.eventmanagement.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/save")
    public ResponseEntity<?> saveTicket(@RequestParam UUID ticketBookingId, @RequestBody Ticket ticket) {
        String message = ticketService.saveTicket(ticketBookingId, ticket);
        if (message.equals("Ticket saved successfully")) return new ResponseEntity<>(message, HttpStatus.CREATED);
        if (message.equals("Tickets are only for PAID ticket bookings")) return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/attendee/{email}")
    public ResponseEntity<List<Ticket>> getTicketsByAttendeeEmail(@PathVariable String email) {
        return new ResponseEntity<>(ticketService.getTicketsByAttendeeEmail(email), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable UUID id) {
        String message = ticketService.deleteTicket(id);
        if (message.equals("Ticket deleted successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
