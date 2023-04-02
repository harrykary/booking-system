package com.hari.kari.booking.service;

import com.hari.kari.booking.model.Show;
import com.hari.kari.booking.repo.ShowRepository;
import com.hari.kari.booking.repo.TicketRepo;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.Seat;
import com.hari.kari.booking.model.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private ShowRepository showRepository;
    
    @Autowired
    private TicketRepo ticketRepo;

    public List<Seat> getAvailableSeats(String showNumber) throws BookingException {
        Show show = showRepository.getShow(showNumber);
        if(show ==null){
            throw new BookingException("Show not found");
        }
        return show.getSeats().stream().filter(seat -> !StringUtils.hasText(seat.getTicketId())).collect(Collectors.toList());
    }


    public String bookSeats(String showNumber, String phoneNumber, String[] seats) throws BookingException {
        Show show= showRepository.getShow(showNumber);
        if(show ==null){
            throw new BookingException("Show not found");
        }
        List<Ticket> bookedTickets = getBookedTickets(showNumber);
        boolean isPhoneUsed = bookedTickets.stream().anyMatch(ticket -> ticket.getPhone().equals(phoneNumber));

        if(isPhoneUsed){
            throw new BookingException("Only one booking per phone number is allowed per show");
        } else{
            Ticket ticket = new Ticket();
            ticket.setPhone(phoneNumber);
            ticket.setShowId(showNumber);
            List<String> toBookSeats = Arrays.stream(seats).collect(Collectors.toList());

            for (Seat seat : show.getSeats()) {
                if(toBookSeats.contains(seat.getSeatId())
                        && !StringUtils.hasText(seat.getTicketId())){
                    seat.setTicketId(ticket.getTicketId());
                    ticket.getSeats().add(seat);
                    toBookSeats.remove(seat.getSeatId());
                }
                if(toBookSeats.isEmpty()){
                    break;
                }
            }
            if(!toBookSeats.isEmpty()){
                ticket.getSeats().forEach(seat -> {
                    seat.setTicketId(null);
                });
                ticket.getSeats().clear();
                throw new BookingException("One or more Seats are invalid/already booked");
            }
            ticketRepo.save(ticket);

            return ticket.getTicketId();
        }
    }

    public List<Ticket> getBookedTickets(String showNumber) throws BookingException {
        Show show= showRepository.getShow(showNumber);
        if(show ==null){
            throw new BookingException("Show not found");
        }
        Set<String> bookedTicketNumbers =  show.getSeats().stream().filter(seat ->  StringUtils.hasText(seat.getTicketId()))
                                        .collect(Collectors.groupingBy(Seat::getTicketId)).keySet();

        List<Ticket> bookedTickets= ticketRepo.getTickets(bookedTicketNumbers);
        log.info("Booked Tickets {}", bookedTickets);

        return bookedTickets;

    }

    public boolean cancelTicket(String ticketId, String phone) throws BookingException {
        Ticket ticket = ticketRepo.getTicket(ticketId);
        if(ticket ==null || !phone.equalsIgnoreCase(ticket.getPhone())){
            throw new BookingException("ticket not found or Phone number is not matched with ticket");
        } else {
            String showId = ticket.getShowId();
            Show show = showRepository.getShow(showId);
            checkCancelThreshold(ticket, show);
            List<Seat> bookedSeats = ticket.getSeats();
            for (Seat seat : show.getSeats()) {
                /*if(StringUtils.hasText(seat.getTicketId()) && bookedSeats.contains(seat)){*/
                if(StringUtils.hasText(seat.getTicketId()) && seat.getTicketId().equals(ticketId)){
                    seat.setTicketId(null);
                    bookedSeats.remove(seat);
                }
                if(bookedSeats.isEmpty()){
                    break;
                }
            }
            ticketRepo.cancelTicket(ticketId);
            showRepository.save(show);
            return true;
        }
    }

    private static void checkCancelThreshold(Ticket ticket, Show show) throws BookingException {
        LocalDateTime cancelBeforeTime = ticket.getCreationTime().plusMinutes(show.getMinsCancellationWindow());
        if (LocalDateTime.now().isAfter(cancelBeforeTime)) {
            log.info("Ticket Booked Time :  {} ", ticket.getCreationTime());
            log.info("Current Time :  {} ", LocalDateTime.now());
            throw new BookingException("Ticket cannot be cancelled as time window of " +  show.getMinsCancellationWindow() + " minutes is crossed");
        }
    }
}
