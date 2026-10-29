package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.Attendee;
import com.ngenzi.eventmanagement.entity.Organizer;
import com.ngenzi.eventmanagement.repository.OrganizerRepository;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

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
}
