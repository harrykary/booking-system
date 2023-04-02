package com.hari.kari.booking.service;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.Show;
import com.hari.kari.booking.repo.ShowRepository;
import com.hari.kari.booking.repo.TicketRepo;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.Seat;
import com.hari.kari.booking.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class BookingServiceTest {

    @Mock
    ShowRepository showRepository;

    @Mock
    TicketRepo ticketRepo;

    @InjectMocks
    BookingService bookingService;


    @BeforeEach
    void setUp(){
        Show show = new Show("SHOW1234", 3, 5, 3);
        List<Seat> seats = new ArrayList<>();
        Seat availableSeat = new Seat("A1");
        seats.add(availableSeat);

        Seat bookedSeat = new Seat("B1");
        bookedSeat.setTicketId("T123456");
        seats.add(bookedSeat);
        show.setSeats(seats);

        Mockito.when(showRepository.getShow("SHOW12345")).thenReturn(show);

    }

    @Test
    void getAvailableSeats() throws BookingException {
        List<Seat> availableSeats= bookingService.getAvailableSeats("SHOW12345");

        Assertions.assertNotNull(availableSeats);
        Assertions.assertEquals(1, availableSeats.size());
    }

    @Test
    void bookSeats() throws BookingException {
        Mockito.doNothing().when(ticketRepo).save(Mockito.any(Ticket.class));
        String ticketNumber= bookingService.bookSeats("SHOW12345", "91234567", new String[]{"A1"});

        Assertions.assertNotNull(ticketNumber);
    }
    @Test
    void should_fail_when_invalid_seats_booked() {
        Mockito.doNothing().when(ticketRepo).save(Mockito.any(Ticket.class));

        assertThrows(BookingException.class, () -> bookingService.bookSeats("SHOW12345", "91234567", new String[]{"C1"}),
                "One or more Seats are invalid/already booked");
    }

    @Test
    void should_fail_when_unavailable_seats_booked() {
        Mockito.doNothing().when(ticketRepo).save(Mockito.any(Ticket.class));
        assertThrows(BookingException.class, () -> bookingService.bookSeats("SHOW12345", "91234567", new String[]{"A1","B1"}),
                "One or more Seats are invalid/already booked");
    }

    @Test
    void should_fail_when_invalid_show_used_seats_booked() {
        Mockito.doNothing().when(ticketRepo).save(Mockito.any(Ticket.class));
        assertThrows(BookingException.class, () -> bookingService.bookSeats("SHOW123456", "91234567", new String[]{"A1","B1"}),
                "One or more Seats are invalid/already booked");
    }


    @Test
    void getBookedTickets() throws BookingException {
        Set<String> bookedTicketNumber = new HashSet<>();
        bookedTicketNumber.add("T123456");
        Mockito.when(ticketRepo.getTickets(bookedTicketNumber)).thenReturn(Collections.EMPTY_LIST);
        List<Ticket> bookedTickets= bookingService.getBookedTickets("SHOW12345");

        Assertions.assertNotNull(bookedTickets);
    }

    @Test
    void should_fail_when_invalid_show_used_to_view_booking_details() {
        assertThrows(BookingException.class, () -> bookingService.getBookedTickets("SHOW123456"),
                "Show not found");
    }

    @Test
    void should_throw_exception_when_invalid_ticket_is_cancelled() throws BookingException {
        Mockito.when(ticketRepo.getTicket("T123456")).thenReturn(null);

        assertThrows(BookingException.class, () -> bookingService.cancelTicket("T123456","91234567"),
                "ticket not found or Phone number is not matched with ticket");
    }

    @Test
    void should_throw_exception_when_invalid_phone_associated_is_cancelled() throws BookingException {
        Ticket ticket = new Ticket();
        ticket.setPhone("90000000");
        Mockito.when(ticketRepo.getTicket("T123456")).thenReturn(ticket);

         assertThrows(BookingException.class, () -> bookingService.cancelTicket("T123456","91234567"),
                "ticket not found or Phone number is not matched with ticket");
    }

    @Test
    void should_cancel_associated_seats_cancelled() throws BookingException {
        Ticket ticket = new Ticket();
        ticket.setPhone("90000000");
        ticket.setShowId("SHOW12345");
        Seat seat = new Seat("A1");
        seat.setTicketId(ticket.getTicketId());
        ticket.getSeats().add(seat);
        Mockito.when(ticketRepo.getTicket(ticket.getTicketId())).thenReturn(ticket);

        Show show = new Show("SHOW12345",3,2,3);
        List<Seat> seats = new ArrayList<>();
        Seat notBookedSeat = new Seat("A2");
        seats.add(notBookedSeat);
        Seat bookedDifferent = new Seat("B2");
        bookedDifferent.setTicketId("T123456");
        seats.add(bookedDifferent);
        seats.add(seat);
        show.setSeats(seats);
        Mockito.when(showRepository.getShow("SHOW12345")).thenReturn(show);

        boolean isCancelled =  bookingService.cancelTicket(ticket.getTicketId(),ticket.getPhone());

        Assertions.assertTrue(isCancelled);

    }
}