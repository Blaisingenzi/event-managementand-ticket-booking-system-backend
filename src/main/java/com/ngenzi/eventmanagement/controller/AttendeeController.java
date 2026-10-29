package com.ngenzi.eventmanagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ngenzi.eventmanagement.entity.Attendee;
import com.ngenzi.eventmanagement.service.AttendeeService;
import com.ngenzi.eventmanagement.service.OrganizerService;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {

    @Autowired
    private AttendeeService attendeeService;

    @Autowired
    private OrganizerService organizerService;

    @PostMapping("/save")
    public ResponseEntity<?> saveAttendee(@RequestBody Attendee attendee) {
        return new ResponseEntity<>(attendeeService.saveAttendee(attendee), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Attendee>> getAllAttendees() {
        return new ResponseEntity<>(attendeeService.getAllAttendees(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendeeById(@PathVariable UUID id) {
        return attendeeService.getAttendeeById(id)
                .<ResponseEntity<?>>map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The attendee with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAttendee(@PathVariable UUID id, @RequestBody Attendee attendee) {
        String message = attendeeService.updateAttendee(id, attendee);
        if (message.equals("Attendee updated successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAttendee(@PathVariable UUID id) {
        String message = attendeeService.deleteAttendee(id);
        if (message.equals("Attendee deleted successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Attendee>> getAttendeesByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(attendeeService.getAttendeesByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/of-organizer/{organizerId}")
    public ResponseEntity<?> getAttendeesByOrganizer(@PathVariable UUID organizerId) {
        Object result = organizerService.getAttendeesByOrganizer(organizerId);
        if (result instanceof String) return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
