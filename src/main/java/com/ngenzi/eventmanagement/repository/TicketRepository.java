package com.ngenzi.eventmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Ticket;
import com.ngenzi.eventmanagement.entity.TicketBooking;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    @Query("SELECT t FROM Ticket t WHERE t.ticketBooking.attendee.email = :email ORDER BY t.issuedDate DESC")
    List<Ticket> findByAttendeeEmail(@Param("email") String email);

    boolean existsByTicketBooking(TicketBooking ticketBooking);
}
