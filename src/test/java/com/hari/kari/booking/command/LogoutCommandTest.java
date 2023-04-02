package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.EmptyParam;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.model.UserContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class LogoutCommandTest {

    @Autowired
    LogoutCommand logoutCommand;

    @Test
    void execute() {
        UserContext.UserContextManager.setUserContext(new UserContext(new User("Hari", UserType.ADMIN)));

        CommandResult result = logoutCommand.execute();

        Assertions.assertInstanceOf(EmptyParam.class, logoutCommand.getParams());
        Assertions.assertNull(UserContext.UserContextManager.getCurrentUserContext());
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
    }

}