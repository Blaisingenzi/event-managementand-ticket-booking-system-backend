package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
}
