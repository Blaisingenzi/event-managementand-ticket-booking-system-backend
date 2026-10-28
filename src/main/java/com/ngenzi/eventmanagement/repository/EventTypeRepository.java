package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {
}
