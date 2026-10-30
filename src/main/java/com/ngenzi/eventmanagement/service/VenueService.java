package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.Venue;
import com.ngenzi.eventmanagement.repository.VenueRepository;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    public String saveVenue(Venue venue) {
        venueRepository.save(venue);
        return "Venue saved successfully";
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Optional<Venue> getVenueById(UUID id) {
        return venueRepository.findById(id);
    }

    public String updateVenue(UUID id, Venue updated) {
        Optional<Venue> existing = venueRepository.findById(id);
        if (existing.isEmpty()) return "The venue with that id does not exist";
        Venue venue = existing.get();
        venue.setHallNumber(updated.getHallNumber());
        venue.setName(updated.getName());
        venueRepository.save(venue);
        return "Venue updated successfully";
    }

    public String deleteVenue(UUID id) {
        if (venueRepository.findById(id).isEmpty()) return "The venue with that id does not exist";
        venueRepository.deleteById(id);
        return "Venue deleted successfully";
    }

    public List<Venue> getFreeVenues() {
        return venueRepository.findFreeVenues();
    }
}
