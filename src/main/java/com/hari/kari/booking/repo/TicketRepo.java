package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.Ticket;

import java.util.List;
import java.util.Set;

public interface TicketRepo {

    void save(Ticket ticket);

    Ticket getTicket(String ticketNumber);

    List<Ticket> getTickets(Set<String> tickets);

    void cancelTicket(String ticketId);
}
