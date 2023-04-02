package com.hari.kari.booking.command;

import com.hari.kari.booking.model.CancelCommandParam;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelCommand implements Command<CancelCommandParam>{

    @Autowired
    private BookingService bookingService;

    private CancelCommandParam cancelCommandParam;

    @Override
    public CommandResult execute() {
        UserContext currentContext = UserContext.UserContextManager.getCurrentUserContext();
        if(currentContext!=null && currentContext.getUser().getUserType().equals(UserType.BUYER)) {
            try {
                bookingService.cancelTicket(getParams().getTicketId(), getParams().getPhone());
                return CommandResult.createSuccessResult("Ticket " + getParams().getTicketId() + " cancelled successfully");
            } catch (BookingException e) {
                log.error("Exception ", e);
                return CommandResult.createFailureResult(e.getMessage());
            }
        }else{
            log.warn("User should login or have Buyer role");
            return CommandResult.createFailureResult("User should login or have Buyer role" );
        }
    }

    @Override
    public void setParams(CancelCommandParam cancelCommandParam) {
        this.cancelCommandParam = cancelCommandParam;

    }

    @Override
    public CancelCommandParam getParams() {
        return cancelCommandParam;
    }
}
