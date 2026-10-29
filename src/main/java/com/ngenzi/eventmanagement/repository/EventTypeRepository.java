package com.ngenzi.eventmanagement.repository;

import java.util.UUID;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {

    @Query("SELECT e FROM EventType e WHERE e.organizers IS EMPTY")
    List<EventType> findUnusedEventTypes();
}
