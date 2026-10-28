package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.EventType;
import com.ngenzi.eventmanagement.repository.EventTypeRepository;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    public String saveEventType(EventType eventType) {
        eventTypeRepository.save(eventType);
        return "Event type saved successfully";
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    public Optional<EventType> getEventTypeById(UUID id) {
        return eventTypeRepository.findById(id);
    }

    public String updateEventType(UUID id, EventType updated) {
        Optional<EventType> existing = eventTypeRepository.findById(id);
        if (existing.isEmpty()) return "The event type with that id does not exist";
        EventType eventType = existing.get();
        eventType.setName(updated.getName());
        eventTypeRepository.save(eventType);
        return "Event type updated successfully";
    }

    public String deleteEventType(UUID id) {
        if (eventTypeRepository.findById(id).isEmpty()) return "The event type with that id does not exist";
        eventTypeRepository.deleteById(id);
        return "Event type deleted successfully";
    }
}
