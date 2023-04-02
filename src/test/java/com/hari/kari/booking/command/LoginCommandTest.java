package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.LoginCommandParam;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class LoginCommandTest {

    @Mock
    UserService userService;

    @Autowired
    LoginCommand loginCommand;

    @Test
    @DisplayName("After login user Context should be created")
    void execute() throws BookingException {
        Mockito.when(userService.getUser("Hari")).thenReturn(new User("Hari", UserType.ADMIN));

        loginCommand.setParams(new LoginCommandParam(new String[]{"Login", "Hari"}));

        CommandResult commandResult = loginCommand.execute();

        Assertions.assertNotNull(UserContext.UserContextManager.getCurrentUserContext());

        assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, commandResult.commandStatus);
    }

    @Test
    @DisplayName("Command response should be in failed Status.")
    void should_result_in_failure() throws BookingException {

        loginCommand.setParams(new LoginCommandParam(new String[]{"Login", "Kari11"}));
        Mockito.when(userService.getUser("Kari11")).thenReturn(null);
        CommandResult commandResult = loginCommand.execute();

        assertEquals(CommandResult.COMMAND_STATUS.FAILURE, commandResult.commandStatus);

        Assertions.assertNull(UserContext.UserContextManager.getCurrentUserContext());

    }
}