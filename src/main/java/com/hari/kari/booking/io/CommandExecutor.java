package com.hari.kari.booking.io;

import com.hari.kari.booking.command.Command;
import com.hari.kari.booking.command.CommandParser;
import com.hari.kari.booking.command.CommandResult;
import com.hari.kari.booking.exception.BookingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommandExecutor {
    @Autowired
    private CommandParser commandParser;

    public CommandResult executeCommand(String input) throws BookingException {
        try {
            Command command = commandParser.parse(input);
            return command.execute();
        }catch (BookingException e){
            log.error("Exception when executing Command " , e);
            return CommandResult.createFailureResult( e.getMessage());
        }
    }
}
