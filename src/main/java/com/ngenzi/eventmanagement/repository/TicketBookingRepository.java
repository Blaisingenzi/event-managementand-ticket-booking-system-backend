package com.ngenzi.eventmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ngenzi.eventmanagement.entity.BookingStatus;
import com.ngenzi.eventmanagement.entity.Organizer;
import com.ngenzi.eventmanagement.entity.TicketBooking;

@Repository
public interface TicketBookingRepository extends JpaRepository<TicketBooking, UUID> {

    List<TicketBooking> findByStatusOrderByShowDateAsc(BookingStatus status);

    List<TicketBooking> findByShowDateBetweenOrderByShowDateAsc(LocalDate start, LocalDate end);

    boolean existsByOrganizerAndShowDateAndStatusNot(Organizer organizer, LocalDate showDate, BookingStatus status);

    long countByShowDate(LocalDate showDate);

    long countByStatus(BookingStatus status);

    @Query("SELECT tb.status, COUNT(tb) FROM TicketBooking tb GROUP BY tb.status")
    List<Object[]> countByStatusGrouped();

    @Modifying
    @Query("UPDATE TicketBooking tb SET tb.status = com.ngenzi.eventmanagement.entity.BookingStatus.CANCELLED WHERE tb.organizer.id = :organizerId AND tb.showDate = :date AND tb.status <> com.ngenzi.eventmanagement.entity.BookingStatus.PAID")
    int cancelDayBookings(@Param("organizerId") UUID organizerId, @Param("date") LocalDate date);
}
