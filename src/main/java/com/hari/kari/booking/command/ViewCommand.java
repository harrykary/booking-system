package com.hari.kari.booking.command;

import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.*;
import com.hari.kari.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;


@Component
@Slf4j
public class ViewCommand implements Command<ViewCommandParam>{

    @Autowired
    private BookingService bookingService;

    private ViewCommandParam viewCommandParam;

    @Override
    public CommandResult execute()  {
        UserContext currentContext = UserContext.UserContextManager.getCurrentUserContext();
        if(currentContext!=null && UserType.ADMIN.equals(currentContext.getUser().getUserType())){
            String showId = getParams().getShowId();
            try {
                List<Ticket> bookedTickets = bookingService.getBookedTickets(showId);
                if(!bookedTickets.isEmpty()){
                    List<String> bookingDetails = new ArrayList<>();
                    bookedTickets.forEach(ticket -> {
                        String bookingDetail = ticket.getShowId() +
                                "," +
                                ticket.getTicketId() +
                                "," +
                                ticket.getPhone() +
                                "," +
                                ticket.getSeats().stream().map(Seat::getSeatId).collect(joining("|"));
                        bookingDetails.add(bookingDetail);
                    });
                    log.info("Booking Details {}", String.join("\n", bookingDetails));
                    return CommandResult.createSuccessResult(String.join("\n", bookingDetails));
                } else {
                    return CommandResult.createSuccessResult("No seats Booked");
                }

            } catch (BookingException e){
                log.error("Exception while checking Booking Details", e);
                return CommandResult.createFailureResult(e.getMessage());
            }
        }else{
            log.warn("User Should login and should have Admin Role ");
            return CommandResult.createFailureResult("User Should login and should have Admin Role" );
        }

    }

    @Override
    public void setParams(ViewCommandParam viewCommandParam) {
        this.viewCommandParam= viewCommandParam;
    }

    @Override
    public ViewCommandParam getParams() {
        return viewCommandParam;
    }
}
