package com.hari.kari.booking.command;

import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.BookCommandParam;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookCommand implements Command<BookCommandParam>{

    @Autowired
    private BookingService bookingService;

    private BookCommandParam bookCommandParam;

    @Override
    public CommandResult execute()  {
        UserContext currentContext = UserContext.UserContextManager.getCurrentUserContext();
        if(currentContext!=null && currentContext.getUser().getUserType().equals(UserType.BUYER)) {
            try {
                String ticketId = bookingService.bookSeats(getParams().getShowId(), getParams().getPhoneNumber(), getParams().getSeats());
                return CommandResult.createSuccessResult(ticketId);
            } catch (BookingException e) {
                log.error("Unable to book seats", e);
                return CommandResult.createFailureResult(e.getMessage());
            }
        }else{
            log.warn("User should login or have Buyer role");
            return CommandResult.createFailureResult("User should login or have Buyer role" );
        }
    }

    @Override
    public void setParams(BookCommandParam bookCommandParam) {
        this.bookCommandParam = bookCommandParam;

    }

    @Override
    public BookCommandParam getParams() {
        return bookCommandParam;
    }
}
