package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.SetupShowCommandParam;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.ShowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class SetupShowCommandTest {


    @Mock
    ShowService showService;

    @InjectMocks
    SetupShowCommand setupShowCommand;

    @Test
    @DisplayName("Should setup the show and it should be stored")
    void execute() throws BookingException {
        String[] commandParams = {"setup", "SHOW1234", "3", "5", "2"};
        setupShowCommand.setParams(new SetupShowCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));

        CommandResult result = setupShowCommand.execute();

        Mockito.verify(showService,Mockito.times(1)).createShow("SHOW1234", 3, 5, 2);
        assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
    }


    @Test
    @DisplayName("Should throw failure response saying that User Should login and should have Admin Role")
    void should_not_set_up_show() throws BookingException {
        String[] commandParams = {"setup", "SHOW1234", "3", "5", "2"};
        setupShowCommand.setParams(new SetupShowCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.BUYER)));

        CommandResult result = setupShowCommand.execute();

        Mockito.verify(showService,Mockito.times(0)).createShow("SHOW1234", 3, 5, 2);
        assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
        assertEquals("User Should login and should have Admin Role", result.getMessage());
    }

    @Test
    @DisplayName("Should not create the show and should fail")
    void should_fail_creating_show() throws BookingException {
        String[] commandParams = {"setup", "SHOW1234", "3", "5", "2"};
        setupShowCommand.setParams(new SetupShowCommandParam(commandParams));
        UserContext.UserContextManager.setUserContext(null);

        CommandResult result = setupShowCommand.execute();

        Mockito.verify(showService,Mockito.times(0)).createShow("SHOW1234", 3, 5, 2);
        assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
    }


    @Test
    @DisplayName("Should throw exception if invalid params passed.")
    void should_throw_exception() throws BookingException {

        assertThrows(BookingException.class, () -> setupShowCommand.setParams(new SetupShowCommandParam(new String[]{"setup", "Kari11"})),
                "Invalid Params");
    }
}