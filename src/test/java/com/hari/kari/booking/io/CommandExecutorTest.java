package com.hari.kari.booking.io;

import com.hari.kari.booking.command.CommandParser;
import com.hari.kari.booking.command.LoginCommand;
import com.hari.kari.booking.command.LogoutCommand;
import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class CommandExecutorTest {

    @Mock
    LoginCommand loginCommand;

    @Mock
    LogoutCommand logoutCommand;

    @Mock
    CommandParser commandParser;

    @InjectMocks
    CommandExecutor commandExecutor;

   /* @Test
    @DisplayName("Should create User context if user is found.")
    void should_create_user_context() throws BookingException {

        UserService userService = Mockito.mock(UserService.class);
        CommandExecutor executor = new CommandExecutor();
        executor.setUserService(userService);
        Mockito.when(userService.getUser("Hari")).thenReturn(new User("Hari", UserType.ADMIN));

        executor.executeCommand("Hari");

        UserContext userContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNotNull( userContext);
        Assertions.assertNotNull(userContext.getUser());
        Assertions.assertEquals("Hari", userContext.getUser().getUserName());
        Assertions.assertEquals(UserType.ADMIN, userContext.getUser().getUserType());
    }
    @Test
    @DisplayName("Should throw exception if user is not found.")
    void should_throw_exception(){

        UserService userService = Mockito.mock(UserService.class);
        CommandExecutor executor = new CommandExecutor();
        executor.setUserService(userService);
        Mockito.when(userService.getUser("Kari11")).thenReturn(null);

        assertThrows(BookingException.class, () -> executor.executeCommand("Kari11"),
                "Invalid User");

    }*/

    @Test
    @DisplayName("Should be able to execute Login Command")
    void should_be_able_to_execute_login() throws BookingException {
        Mockito.when(commandParser.parse("Login Hari")).thenReturn(loginCommand);

        commandExecutor.executeCommand("Login Hari");

        Mockito.verify(loginCommand,Mockito.times(1)).execute();
    }

    @Test
    @DisplayName("Should be able to execute Logout Command")
    void should_be_able_to_execute_logout() throws BookingException {
        Mockito.when(commandParser.parse("Logout")).thenReturn(logoutCommand);

        commandExecutor.executeCommand("Logout");

        Mockito.verify(logoutCommand,Mockito.times(1)).execute();

    }
}