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
import org.springframework.web.bind.annotation.RestController;

import com.ngenzi.eventmanagement.entity.EventType;
import com.ngenzi.eventmanagement.service.EventTypeService;

@RestController
@RequestMapping("/api/event-types")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEventType(@RequestBody EventType eventType) {
        return new ResponseEntity<>(eventTypeService.saveEventType(eventType), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        return new ResponseEntity<>(eventTypeService.getAllEventTypes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventTypeById(@PathVariable UUID id) {
        return eventTypeService.getEventTypeById(id)
                .<ResponseEntity<?>>map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The event type with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEventType(@PathVariable UUID id, @RequestBody EventType eventType) {
        String message = eventTypeService.updateEventType(id, eventType);
        if (message.equals("Event type updated successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEventType(@PathVariable UUID id) {
        String message = eventTypeService.deleteEventType(id);
        if (message.equals("Event type deleted successfully")) return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<EventType>> getUnusedEventTypes() {
        return new ResponseEntity<>(eventTypeService.getUnusedEventTypes(), HttpStatus.OK);
    }
}
