package com.ngenzi.eventmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngenzi.eventmanagement.entity.Attendee;
import com.ngenzi.eventmanagement.repository.AttendeeRepository;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepository attendeeRepository;

    public String saveAttendee(Attendee attendee) {
        attendeeRepository.save(attendee);
        return "Attendee saved successfully";
    }

    public List<Attendee> getAllAttendees() {
        return attendeeRepository.findAll();
    }

    public Optional<Attendee> getAttendeeById(UUID id) {
        return attendeeRepository.findById(id);
    }

    public String updateAttendee(UUID id, Attendee updated) {
        Optional<Attendee> existing = attendeeRepository.findById(id);
        if (existing.isEmpty()) return "The attendee with that id does not exist";
        Attendee attendee = existing.get();
        attendee.setFirstName(updated.getFirstName());
        attendee.setLastName(updated.getLastName());
        attendee.setEmail(updated.getEmail());
        attendee.setPhone(updated.getPhone());
        attendeeRepository.save(attendee);
        return "Attendee updated successfully";
    }

    public String deleteAttendee(UUID id) {
        if (attendeeRepository.findById(id).isEmpty()) return "The attendee with that id does not exist";
        attendeeRepository.deleteById(id);
        return "Attendee deleted successfully";
    }
}
