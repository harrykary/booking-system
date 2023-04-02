package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.BookCommandParam;
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
public class BookCommandTest {

    @Mock
    BookingService bookingService;

    @InjectMocks
    BookCommand bookCommand;

    @Test
    @DisplayName("Buyer should book seats if they are available")
    void execute() throws BookingException {
        String[] commandParams = {"book", "SHOW12345", "91234567", "A1,A2,A3"};
        bookCommand.setParams(new BookCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));

        CommandResult result = bookCommand.execute();

        Mockito.verify(bookingService,Mockito.times(1)).bookSeats("SHOW12345", "91234567", new String[]{"A1","A2","A3"});
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
    }

    @Test
    @DisplayName("Buyer should book seats if they are available")
    void should_send_failure_response() throws BookingException {
        String[] commandParams = {"book", "SHOW12345", "91234567", "A1,A2,A3"};
        bookCommand.setParams(new BookCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));
        Mockito.when(bookingService.bookSeats("SHOW12345", "91234567", new String[]{"A1","A2","A3"})).thenThrow(new BookingException("Unable to book"));

        CommandResult result = bookCommand.execute();

        Mockito.verify(bookingService,Mockito.times(1)).bookSeats("SHOW12345", "91234567", new String[]{"A1","A2","A3"});
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("Should send failure response when user is not logged in")
    void should_send_failure_response_when_user_not_logged() throws BookingException {
        String[] commandParams = {"book", "SHOW12345", "91234567", "A1,A2,A3"};
        bookCommand.setParams(new BookCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(null);

        CommandResult result = bookCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).bookSeats("SHOW12345", "91234567", new String[]{"A1","A2","A3"});
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("Should send failure response when user is not buyer")
    void should_send_failure_response_when_user_not_buyer() throws BookingException {
        String[] commandParams = {"book", "SHOW12345", "91234567", "A1,A2,A3"};
        bookCommand.setParams(new BookCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));

        CommandResult result = bookCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).bookSeats("SHOW12345", "91234567", new String[]{"A1","A2","A3"});
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }
}