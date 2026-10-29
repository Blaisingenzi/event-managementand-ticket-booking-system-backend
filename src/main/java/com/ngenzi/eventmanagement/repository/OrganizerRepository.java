package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

    @Query("SELECT o FROM Organizer o JOIN o.eventTypes e WHERE LOWER(e.name) = LOWER(:name)")
    List<Organizer> findByEventTypeName(@Param("name") String name);

    @Query("SELECT DISTINCT tb.attendee FROM TicketBooking tb WHERE tb.organizer.id = :organizerId")
    List<com.ngenzi.eventmanagement.entity.Attendee> findAttendeesByOrganizerId(@Param("organizerId") UUID organizerId);
}
