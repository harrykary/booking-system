package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemTicketRepository implements TicketRepo {

    private static final Map<String, Ticket> tickets=new HashMap<>();


    @Override
    public void save(Ticket ticket) {
        tickets.put(ticket.getTicketId(),ticket);
    }

    @Override
    public Ticket getTicket(String ticketNumber) {
        return tickets.get(ticketNumber);
    }

    @Override
    public List<Ticket> getTickets(Set<String> ticketNumbers) {
        List<Ticket> bookedTickets = new ArrayList<>();
        ticketNumbers.forEach(ticketNumber -> bookedTickets.add(tickets.get(ticketNumber)));
        return bookedTickets;
    }

    @Override
    public void cancelTicket(String ticketId) {
        tickets.remove(ticketId);
    }
}
