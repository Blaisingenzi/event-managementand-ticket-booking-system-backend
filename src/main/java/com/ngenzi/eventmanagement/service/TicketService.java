package com.ngenzi.eventmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.entity.Ticket;
import com.ngenzi.eventmanagement.entity.TicketBooking;
import com.ngenzi.eventmanagement.repository.TicketBookingRepository;
import com.ngenzi.eventmanagement.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    public String saveTicket(UUID ticketBookingId, Ticket ticket) {
        Optional<TicketBooking> booking = ticketBookingRepository.findById(ticketBookingId);
        if (booking.isEmpty()) return "The ticket booking with that id does not exist";
        if (booking.get().getStatus() != BookingStatus.PAID) return "Tickets are only for PAID ticket bookings";
        ticket.setIssuedDate(LocalDate.now());
        ticket.setTicketBooking(booking.get());
        ticketRepository.save(ticket);
        return "Ticket saved successfully";
    }

    public List<Ticket> getTicketsByAttendeeEmail(String email) {
        return ticketRepository.findByAttendeeEmail(email);
    }

    public String deleteTicket(UUID id) {
        if (ticketRepository.findById(id).isEmpty()) return "The ticket with that id does not exist";
        ticketRepository.deleteById(id);
        return "Ticket deleted successfully";
    }

    public boolean existsByTicketBooking(TicketBooking ticketBooking) {
        return ticketRepository.existsByTicketBooking(ticketBooking);
    }
}
