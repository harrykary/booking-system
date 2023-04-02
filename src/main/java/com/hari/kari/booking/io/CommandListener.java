package com.hari.kari.booking.io;

import com.hari.kari.booking.command.CommandResult;
import com.hari.kari.booking.exception.BookingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

@Slf4j
public class CommandListener implements Listener{
    boolean isStarted = false;

    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public void start() {
        isStarted= true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (isStarted) {
                    System.out.println("Enter command: ");
                    String input = scanner.nextLine();
                    try {
                        CommandResult result = acceptCommand(input);
                        System.out.println(result.getMessage());
                        log.info("Command Result {}", result);
                    } catch (BookingException e) {
                        log.error("" + e);
                    }
            }
        }
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
