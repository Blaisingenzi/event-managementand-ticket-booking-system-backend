package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, UUID> {
}
