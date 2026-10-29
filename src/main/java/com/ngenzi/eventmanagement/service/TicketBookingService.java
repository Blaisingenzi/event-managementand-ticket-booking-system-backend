package com.ngenzi.eventmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.entity.TicketBooking;
import com.ngenzi.eventmanagement.repository.TicketBookingRepository;

@Service
public class TicketBookingService {

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    public String saveTicketBooking(TicketBooking ticketBooking) {
        if (ticketBooking.getShowDate().isBefore(LocalDate.now()))
            return "Ticket booking date cannot be in the past";
        if (ticketBookingRepository.existsByOrganizerIdAndShowDateAndStatusNot(
                ticketBooking.getOrganizer().getId(), ticketBooking.getShowDate(), BookingStatus.CANCELLED))
            return "Organizer already has a show booked on that date";
        ticketBooking.setStatus(BookingStatus.PENDING);
        ticketBookingRepository.save(ticketBooking);
        return "Ticket booking saved successfully";
    }

    public List<TicketBooking> getAllTicketBookings() {
        return ticketBookingRepository.findAll();
    }

    public Optional<TicketBooking> getTicketBookingById(UUID id) {
        return ticketBookingRepository.findById(id);
    }

    public String updateTicketBooking(UUID id, TicketBooking updated) {
        Optional<TicketBooking> existing = ticketBookingRepository.findById(id);
        if (existing.isEmpty()) return "The ticket booking with that id does not exist";
        TicketBooking ticketBooking = existing.get();
        ticketBooking.setShowDate(updated.getShowDate());
        ticketBooking.setShowName(updated.getShowName());
        ticketBooking.setSeats(updated.getSeats());
        ticketBooking.setAttendee(updated.getAttendee());
        ticketBooking.setOrganizer(updated.getOrganizer());
        ticketBookingRepository.save(ticketBooking);
        return "Ticket booking updated successfully";
    }

    public String updateTicketBookingStatus(UUID id, BookingStatus newStatus) {
        Optional<TicketBooking> existing = ticketBookingRepository.findById(id);
        if (existing.isEmpty()) return "The ticket booking with that id does not exist";
        BookingStatus current = existing.get().getStatus();
        boolean allowed = (current == BookingStatus.PENDING && (newStatus == BookingStatus.RESERVED || newStatus == BookingStatus.CANCELLED))
                || (current == BookingStatus.RESERVED && (newStatus == BookingStatus.PAID || newStatus == BookingStatus.CANCELLED));
        if (!allowed) return "Cannot change status from " + current + " to " + newStatus;
        existing.get().setStatus(newStatus);
        ticketBookingRepository.save(existing.get());
        return "Ticket booking status updated successfully";
    }

    public String deleteTicketBooking(UUID id) {
        if (ticketBookingRepository.findById(id).isEmpty()) return "The ticket booking with that id does not exist";
        ticketBookingRepository.deleteById(id);
        return "Ticket booking deleted successfully";
    }

    public List<TicketBooking> getTicketBookingsByStatus(BookingStatus status) {
        return ticketBookingRepository.findByStatusOrderByShowDateAsc(status);
    }

    public List<TicketBooking> getTicketBookingsBetween(LocalDate start, LocalDate end) {
        return ticketBookingRepository.findByShowDateBetweenOrderByShowDateAsc(start, end);
    }
}
