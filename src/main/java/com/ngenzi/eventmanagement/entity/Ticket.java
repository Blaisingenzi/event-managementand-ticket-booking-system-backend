package com.ngenzi.eventmanagement.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String ticketClass;

    private String seatInfo;

    private int priceRwf;

    private LocalDate issuedDate;

    @ManyToOne
    @JoinColumn(name = "ticket_booking_id")
    private TicketBooking ticketBooking;

    public UUID getId() { return id; }

    public String getTicketClass() { return ticketClass; }
    public void setTicketClass(String ticketClass) { this.ticketClass = ticketClass; }

    public String getSeatInfo() { return seatInfo; }
    public void setSeatInfo(String seatInfo) { this.seatInfo = seatInfo; }

    public int getPriceRwf() { return priceRwf; }
    public void setPriceRwf(int priceRwf) { this.priceRwf = priceRwf; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public TicketBooking getTicketBooking() { return ticketBooking; }
    public void setTicketBooking(TicketBooking ticketBooking) { this.ticketBooking = ticketBooking; }
}
