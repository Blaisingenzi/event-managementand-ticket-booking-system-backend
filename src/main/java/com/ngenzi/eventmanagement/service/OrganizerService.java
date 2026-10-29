package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.Attendee;
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

    public Object getAttendeesByOrganizer(UUID organizerId) {
        if (organizerRepository.findById(organizerId).isEmpty()) return "The organizer with that id does not exist";
        return organizerRepository.findAttendeesByOrganizerId(organizerId);
    }

    public String addEventTypeToOrganizer(UUID organizerId, UUID eventTypeId) {
        Optional<Organizer> org = organizerRepository.findById(organizerId);
        if (org.isEmpty()) return "The organizer with that id does not exist";
        Optional<EventType> et = eventTypeRepository.findById(eventTypeId);
        if (et.isEmpty()) return "That event type does not exist";
        if (org.get().getEventTypes().contains(et.get())) return "The organizer already has that event type";
        org.get().getEventTypes().add(et.get());
        organizerRepository.save(org.get());
        return "Event type added to the organizer";
    }

    public String moveOrganizerToVenue(UUID organizerId, int hallNumber) {
        Optional<Organizer> org = organizerRepository.findById(organizerId);
        if (org.isEmpty()) return "The organizer with that id does not exist";
        Optional<Venue> venue = venueRepository.findByHallNumber(hallNumber);
        if (venue.isEmpty()) return "The venue with that hallNumber does not exist";
        if (organizerRepository.existsByVenueId(venue.get().getId()) &&
                !venue.get().getId().equals(org.get().getVenue().getId()))
            return "That venue is taken by another organizer";
        org.get().setVenue(venue.get());
        organizerRepository.save(org.get());
        return "Venue changed successfully";
    }
}
