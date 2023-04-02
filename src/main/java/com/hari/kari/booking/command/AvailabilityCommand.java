package com.hari.kari.booking.command;

import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.AvailabilityCommandParam;
import com.hari.kari.booking.model.Seat;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.joining;


@Component
@Slf4j
public class AvailabilityCommand implements Command<AvailabilityCommandParam>{

    @Autowired
    private BookingService bookingService;

    private AvailabilityCommandParam availabilityCommandParam;

    @Override
    public CommandResult execute()  {
        UserContext currentContext = UserContext.UserContextManager.getCurrentUserContext();
        if(currentContext!=null && currentContext.getUser().getUserType().equals(UserType.BUYER)) {
            String showId = getParams().getShowId();
            List<Seat> seats;
            try {
                seats = bookingService.getAvailableSeats(showId);
                if(!seats.isEmpty()){
                    String availableSeats = seats.stream().map(Seat::getSeatId).collect(joining(","));
                    log.info("Available Seats: {}", availableSeats);
                    return CommandResult.createSuccessResult(availableSeats);
                } else {
                    return CommandResult.createFailureResult("No available seats");
                }
            } catch (BookingException e){
                log.error("Exception while checking seats", e);
                return CommandResult.createFailureResult(e.getMessage());
            }
        }else{
            log.warn("User should login or have Buyer role");
            return CommandResult.createFailureResult("User should login or have Buyer role" );
        }

    }

    @Override
    public void setParams(AvailabilityCommandParam availabilityCommandParam) {
        this.availabilityCommandParam= availabilityCommandParam;
    }

    @Override
    public AvailabilityCommandParam getParams() {
        return availabilityCommandParam;
    }
}
