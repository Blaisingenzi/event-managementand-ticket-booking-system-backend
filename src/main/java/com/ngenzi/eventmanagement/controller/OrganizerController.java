package com.ngenzi.eventmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngenzi.eventmanagement.entity.Organizer;
import com.ngenzi.eventmanagement.service.OrganizerService;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping("/save")
    public ResponseEntity<?> saveOrganizer(@RequestBody Organizer organizer) {
        return new ResponseEntity<>(organizerService.saveOrganizer(organizer), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        return new ResponseEntity<>(organizerService.getAllOrganizers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrganizerById(@PathVariable UUID id) {
        return organizerService.getOrganizerById(id)
                .<ResponseEntity<?>>map(o -> new ResponseEntity<>(o, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The organizer with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateOrganizer(@PathVariable UUID id, @RequestBody Organizer organizer) {
        String message = organizerService.updateOrganizer(id, organizer);
        if (message.equals("Organizer updated successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrganizer(@PathVariable UUID id) {
        String message = organizerService.deleteOrganizer(id);
        if (message.equals("Organizer deleted successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-event-type")
    public ResponseEntity<List<Organizer>> getOrganizersByEventType(@RequestParam String name) {
        return new ResponseEntity<>(organizerService.getOrganizersByEventType(name), HttpStatus.OK);
    }

    @PutMapping("/{organizerId}/event-types/{eventTypeId}")
    public ResponseEntity<?> addEventType(@PathVariable UUID organizerId, @PathVariable UUID eventTypeId) {
        String message = organizerService.addEventType(organizerId, eventTypeId);
        if (message.equals("Event type added to the organizer")) return new ResponseEntity<>(message, HttpStatus.OK);
        if (message.equals("The organizer already has that event type")) return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{organizerId}/venue")
    public ResponseEntity<?> changeVenue(@PathVariable UUID organizerId, @RequestParam int hallNumber) {
        String message = organizerService.changeVenue(organizerId, hallNumber);
        if (message.equals("Venue changed successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        if (message.equals("That venue is taken by another organizer")) return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
