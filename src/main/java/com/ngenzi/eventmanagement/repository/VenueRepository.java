package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, UUID> {

    @Query("SELECT v FROM Venue v WHERE NOT EXISTS (SELECT o FROM Organizer o WHERE o.venue = v) ORDER BY v.hallNumber")
    List<Venue> findFreeVenues();
}
