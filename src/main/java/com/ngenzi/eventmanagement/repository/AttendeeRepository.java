package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.Attendee;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, UUID> {
}
