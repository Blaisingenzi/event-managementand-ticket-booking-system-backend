package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.EventType;
import com.ngenzi.eventmanagement.entity.Organizer;
import com.ngenzi.eventmanagement.entity.Venue;
import com.ngenzi.eventmanagement.repository.EventTypeRepository;
import com.ngenzi.eventmanagement.repository.OrganizerRepository;
import com.ngenzi.eventmanagement.repository.VenueRepository;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private VenueRepository venueRepository;

    public String saveOrganizer(Organizer organizer) {
        organizerRepository.save(organizer);
        return "Organizer saved successfully";
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepository.findAll();
    }

    public Optional<Organizer> getOrganizerById(UUID id) {
        return organizerRepository.findById(id);
    }

    public String updateOrganizer(UUID id, Organizer updated) {
        Optional<Organizer> existing = organizerRepository.findById(id);
        if (existing.isEmpty()) return "The organizer with that id does not exist";
        Organizer organizer = existing.get();
        organizer.setFirstName(updated.getFirstName());
        organizer.setLastName(updated.getLastName());
        organizer.setCompany(updated.getCompany());
        organizer.setVenue(updated.getVenue());
        organizerRepository.save(organizer);
        return "Organizer updated successfully";
    }

    public String deleteOrganizer(UUID id) {
        if (organizerRepository.findById(id).isEmpty()) return "The organizer with that id does not exist";
        organizerRepository.deleteById(id);
        return "Organizer deleted successfully";
    }

    public List<Organizer> getOrganizersByEventType(String name) {
        return organizerRepository.findByEventTypeName(name);
    }

    public String addEventType(UUID organizerId, UUID eventTypeId) {
        Optional<Organizer> existingOrganizer = organizerRepository.findById(organizerId);
        if (existingOrganizer.isEmpty()) return "The organizer with that id does not exist";
        Optional<EventType> existingEventType = eventTypeRepository.findById(eventTypeId);
        if (existingEventType.isEmpty()) return "That event type does not exist";
        Organizer organizer = existingOrganizer.get();
        EventType eventType = existingEventType.get();
        if (organizer.getEventTypes().contains(eventType)) return "The organizer already has that event type";
        organizer.getEventTypes().add(eventType);
        organizerRepository.save(organizer);
        return "Event type added to the organizer";
    }

    public String changeVenue(UUID organizerId, int hallNumber) {
        Optional<Organizer> existingOrganizer = organizerRepository.findById(organizerId);
        if (existingOrganizer.isEmpty()) return "The organizer with that id does not exist";
        Optional<Venue> venue = venueRepository.findByHallNumber(hallNumber);
        if (venue.isEmpty()) return "The venue with that hallNumber does not exist";
        Organizer organizer = existingOrganizer.get();
        Venue current = organizer.getVenue();
        if (organizerRepository.existsByVenue(venue.get())
                && (current == null || !venue.get().equals(current)))
            return "That venue is taken by another organizer";
        organizer.setVenue(venue.get());
        organizerRepository.save(organizer);
        return "Venue changed successfully";
    }
}
