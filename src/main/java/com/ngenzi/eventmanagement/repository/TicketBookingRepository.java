package com.ngenzi.eventmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.entity.TicketBooking;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, UUID> {

    List<TicketBooking> findByStatusOrderByShowDateAsc(BookingStatus status);

    List<TicketBooking> findByShowDateBetweenOrderByShowDateAsc(LocalDate start, LocalDate end);
}
