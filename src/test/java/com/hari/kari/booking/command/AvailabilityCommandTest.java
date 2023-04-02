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
public class AvailabilityCommandTest {

    @Mock
    BookingService bookingService;

    @InjectMocks
    AvailabilityCommand availabilityCommand;

    @Test
    @DisplayName("Should show available seats for a show")
    void execute() throws BookingException {
        String[] commandParams = {"availability", "SHOW1234"};
        availabilityCommand.setParams(new AvailabilityCommandParam(commandParams));
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat("A1");
        seats.add(seat);
        Mockito.when(bookingService.getAvailableSeats("SHOW1234")).thenReturn(seats);
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));

        CommandResult result = availabilityCommand.execute();

        Mockito.verify(bookingService,Mockito.times(1)).getAvailableSeats("SHOW1234");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
    }

    @Test
    @DisplayName("Should result in failure if no available seats for a show")
    void should_give_failure_if_no_seats() throws BookingException {
        String[] commandParams = {"availability", "SHOW1234"};
        availabilityCommand.setParams(new AvailabilityCommandParam(commandParams));
        Mockito.when(bookingService.getAvailableSeats("SHOW1234")).thenReturn(new ArrayList<Seat>());
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));

        CommandResult result = availabilityCommand.execute();

        Mockito.verify(bookingService,Mockito.times(1)).getAvailableSeats("SHOW1234");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("Should fail saying user should login")
    void should_login_before_execution() throws BookingException {
        String[] commandParams = {"availability", "SHOW1234"};
        availabilityCommand.setParams(new AvailabilityCommandParam(commandParams));
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat("A1");
        seats.add(seat);
        Mockito.when(bookingService.getAvailableSeats("SHOW1234")).thenReturn(seats);
        UserContext.UserContextManager.setUserContext(null);

        CommandResult result = availabilityCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).getAvailableSeats("SHOW1234");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

    @Test
    @DisplayName("Should fail saying user should login")
    void should_have_buyer_before_execution() throws BookingException {
        String[] commandParams = {"availability", "SHOW1234"};
        availabilityCommand.setParams(new AvailabilityCommandParam(commandParams));
        List<Seat> seats = new ArrayList<>();
        Seat seat = new Seat("A1");
        seats.add(seat);
        Mockito.when(bookingService.getAvailableSeats("SHOW1234")).thenReturn(seats);
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));

        CommandResult result = availabilityCommand.execute();

        Mockito.verify(bookingService,Mockito.times(0)).getAvailableSeats("SHOW1234");
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }

}