package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.CancelCommandParam;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class CancelCommandTest {

    @Mock
    BookingService bookingService;

    @InjectMocks
    CancelCommand cancelCommand;

    @Test
    @DisplayName("Should cancel booked tickets")
    void execute() throws BookingException {
        String[] commandParams = {"cancel", "T1234567" ,"91234567"};
        cancelCommand.setParams(new CancelCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Kari", UserType.BUYER)));

        CommandResult result = cancelCommand.execute();

        Mockito.verify(bookingService,Mockito.times(1)).cancelTicket("T1234567" ,"91234567");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
    }

    @Test
    @DisplayName("Should send failure response when user is not logged in")
    void should_send_failure_response_when_not_logged_in() throws BookingException {
        String[] commandParams = {"cancel", "T1234567" ,"91234567"};
        cancelCommand.setParams(new CancelCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(null);

        CommandResult result = cancelCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).cancelTicket("T1234567" ,"91234567");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("Should send failure response when user is not logged in")
    void should_send_failure_response_when_user_is_not_buyer() throws BookingException {
        String[] commandParams = {"cancel", "T1234567" ,"91234567"};
        cancelCommand.setParams(new CancelCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));

        CommandResult result = cancelCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).cancelTicket("T1234567" ,"91234567");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }
    @Test
    @DisplayName("Should send failure response when ticket not found")
    void should_send_failure_response_when_ticket_not_found() throws BookingException {
        String[] commandParams = {"cancel", "T1234567" ,"91234567"};
        cancelCommand.setParams(new CancelCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Kari", UserType.BUYER)));
        Mockito.when(bookingService.cancelTicket("T1234567" ,"91234567")).thenThrow(new BookingException("Ticket not found"));

        CommandResult result = cancelCommand.execute();
        System.out.println(result);

        Mockito.verify(bookingService,Mockito.times(1)).cancelTicket("T1234567" ,"91234567");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

}