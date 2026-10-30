package com.ngenzi.eventmanagement.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.repository.AttendeeRepository;
import com.ngenzi.eventmanagement.repository.OrganizerRepository;
import com.ngenzi.eventmanagement.repository.TicketBookingRepository;
import com.ngenzi.eventmanagement.repository.TicketRepository;
import com.ngenzi.eventmanagement.repository.VenueRepository;

@Service
public class DashboardService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private TicketBookingRepository ticketBookingRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("attendees", attendeeRepository.count());
        summary.put("organizers", organizerRepository.count());
        summary.put("freeVenues", (long) venueRepository.findFreeVenues().size());
        summary.put("bookingsToday", ticketBookingRepository.countByShowDate(LocalDate.now()));
        summary.put("pending", ticketBookingRepository.countByStatus(BookingStatus.PENDING));
        summary.put("tickets", ticketRepository.count());
        return summary;
    }
}
