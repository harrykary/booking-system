package com.hari.kari.booking.io;

import com.hari.kari.booking.exception.BookingException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Disabled as testing with LineCommand Listener")
public class CommandListenerTest {
    @Mock
    CommandExecutor commandExecutor;

    @InjectMocks
    CommandListener commandListener;
    @Test
    void should_be_able_call_command_executor() throws BookingException {
        commandListener.start();

        commandListener.acceptCommand("Login Hari");

        Mockito.verify(commandExecutor,Mockito.times(1)).executeCommand("Login Hari");
    }

}