package com.ngenzi.eventmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Attendee;
import com.ngenzi.eventmanagement.entity.Organizer;
import com.ngenzi.eventmanagement.entity.Venue;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

    @Query("SELECT o FROM Organizer o JOIN o.eventTypes e WHERE LOWER(e.name) = LOWER(:name)")
    List<Organizer> findByEventTypeName(@Param("name") String name);

    @Query("SELECT DISTINCT tb.attendee FROM TicketBooking tb WHERE tb.organizer.id = :organizerId")
    List<Attendee> findAttendeesByOrganizerId(@Param("organizerId") UUID organizerId);

    boolean existsByVenue(Venue venue);
}
