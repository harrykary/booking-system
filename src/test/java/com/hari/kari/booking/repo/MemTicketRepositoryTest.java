package com.hari.kari.booking.repo;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class MemTicketRepositoryTest {

    @Autowired
    MemTicketRepository memTicketRepository;

    @Test
    void save() {

        Ticket ticket = new Ticket();

        memTicketRepository.save(ticket);

        Assertions.assertNotNull(memTicketRepository.getTicket(ticket.getTicketId()));
    }

}