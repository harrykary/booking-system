package com.hari.kari.booking.command;

import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.io.CommandExecutor;
import com.hari.kari.booking.io.Listener;
import org.springframework.beans.factory.annotation.Autowired;

public class LineCommandListener implements Listener {

    boolean isStarted = false;
    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public void start() {
        isStarted = true;
    }

    @Override
    public void stop() {
        isStarted= false;
    }

    @Override
    public CommandResult acceptCommand(String command) throws BookingException {
        if(!isStarted){
            throw new BookingException("Command listener not started");
        }
        return commandExecutor.executeCommand(command);
    }
}
