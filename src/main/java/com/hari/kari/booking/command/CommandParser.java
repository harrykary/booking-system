package com.hari.kari.booking.command;

import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandParser {

    @Autowired
    LoginCommand loginCommand;

    @Autowired
    LogoutCommand logoutCommand;

    @Autowired
    SetupShowCommand setupShowCommand;

    @Autowired
    AvailabilityCommand availabilityCommand;

    @Autowired
    BookCommand bookCommand;

    @Autowired
    QuitCommand quitCommand;

    @Autowired
    ViewCommand viewCommand;

    @Autowired
    CancelCommand cancelCommand;

    public enum Commands{
        LOGIN, LOGOUT, SETUP, AVAILABILITY,BOOK,QUIT,VIEW,CANCEL, UNKNOWN;

       /* private String command;

        Commands(String command) {
            this.command=command;
        }*/
        public static Commands get(String command) throws BookingException {
            switch(command) {
                case "Login":
                case "LOGIN":
                case "login":
                    return LOGIN;
                case "logout":
                case "LOGOUT":
                case "Logout":
                    return LOGOUT;
                case "setup":
                case "SETUP":
                case "Setup":
                    return SETUP;
                case "Availability":
                case "availability":
                case "AVAILABILITY":
                    return AVAILABILITY;
                case "Book":
                case "book":
                case "BOOK":
                    return BOOK;
                case "Quit":
                case "quit":
                case "QUIT":
                    return QUIT;
                case "View":
                case "view":
                case "VIEW":
                    return VIEW;
                case "Cancel":
                case "CANCEL":
                case "cancel":
                    return CANCEL;
                default:
                    return UNKNOWN;
            }
        }

       /* @Override
        public String toString() {return command;}*/
    }


    public Command parse(String input) throws BookingException {
        String[] inputValues = input.split(" ");
        Commands command = Commands.get(inputValues[0]);
        log.info("Command Passed {}", command);
        switch (command){
            case LOGIN:
                loginCommand.setParams(new LoginCommandParam(inputValues));
                return loginCommand;
            case SETUP:
                setupShowCommand.setParams(new SetupShowCommandParam(inputValues));
                return setupShowCommand;
            case LOGOUT:
                logoutCommand.setParams(new EmptyParam(inputValues));
                return logoutCommand;
            case QUIT:
                quitCommand.setParams(new EmptyParam(inputValues));
                return quitCommand;
            case AVAILABILITY:
                availabilityCommand.setParams(new AvailabilityCommandParam(inputValues));
                return availabilityCommand;
            case BOOK:
                bookCommand.setParams(new BookCommandParam(inputValues));
                return bookCommand;
            case VIEW:
                viewCommand.setParams(new ViewCommandParam(inputValues));
                return viewCommand;
            case CANCEL:
                cancelCommand.setParams(new CancelCommandParam(inputValues));
                return cancelCommand;
            default:
                throw new BookingException("Invalid Command Passed");
        }
    }
}
