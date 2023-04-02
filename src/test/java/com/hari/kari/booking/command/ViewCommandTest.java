package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.*;
import com.hari.kari.booking.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class ViewCommandTest {

    @Mock
    BookingService bookingService;
    @InjectMocks
    ViewCommand viewCommand;

    @Test
    @DisplayName("Buyer should not able to view any show level booking details")
    void execute() throws BookingException {

        String[] commandParams = {"View", "SHOW12345"};
        viewCommand.setParams(new ViewCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));

        CommandResult result = viewCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).getBookedTickets("SHOW12345");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);

    }

    @Test
    @DisplayName("Admin should be able to view any show level booking details")
    void should_view_booking_details() throws BookingException {

        String[] commandParams = {"View", "SHOW12345"};
        viewCommand.setParams(new ViewCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));
        List<Ticket> bookedTickets = new ArrayList<>();
        Mockito.when(bookingService.getBookedTickets("SHOW12345")).thenReturn(bookedTickets);

        CommandResult result = viewCommand.execute();

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);

    }



    @Test
    @DisplayName("When User is not logged in")
    void view_should_show_failure_booking_details() throws BookingException {

        String[] commandParams = {"View", "SHOW12345"};
        viewCommand.setParams(new ViewCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(null);

        CommandResult result = viewCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).getBookedTickets("SHOW12345");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("When Show is not available")
    void should_failure_to_view_booking_details() throws BookingException {

        String[] commandParams = {"View", "SHOW12345"};
        viewCommand.setParams(new ViewCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));
        Mockito.when(bookingService.getBookedTickets("SHOW12345")).thenThrow(new BookingException("Show Not found"));

        CommandResult result = viewCommand.execute();

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);

    }
}