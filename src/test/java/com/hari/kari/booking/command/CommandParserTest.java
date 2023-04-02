package com.hari.kari.booking.command;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class CommandParserTest {

    @Autowired
    CommandParser commandParser;
    @Test
    @DisplayName("When command starts with login it should return Login command")
    void should_return_login_command() throws BookingException {

        Command command = commandParser.parse("Login Hari");

        Assertions.assertInstanceOf(LoginCommand.class, command);
        Assertions.assertEquals("Hari", ((LoginCommand)command).getParams().getUserName());

    }
    @DisplayName("{index} - {0} When login command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"login", "login hari kari"})
    void should_throw_invalid_params_login_command(String loginCmd)
    {

        assertThrows(BookingException.class, () -> commandParser.parse(loginCmd),
                "Invalid input");

    }
    @Test
    @DisplayName("When command starts with logout it should return logout command")
    void should_return_logout_command() throws BookingException {
        Command command = commandParser.parse("Logout");

        Assertions.assertInstanceOf(LogoutCommand.class, command);
    }

    @Test
    @DisplayName("When command starts with Setup it should return logout command")
    void should_return_setup_show_command() throws BookingException {
        Command command = commandParser.parse("Setup show1234 3 5 2");

        Assertions.assertInstanceOf(SetupShowCommand.class, command);
    }

    @DisplayName("{index} - {0} When Setup Show command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"Setup show1234 3 5", "Setup show1234 3 5 2 3"})
    void should_throw_invalid_params_setup_show_command(String setupShowCmd) {

        assertThrows(BookingException.class, () -> commandParser.parse(setupShowCmd),
                "Invalid input");

    }

    @Test
    @DisplayName("Should throw exception if invalid Command passed.")
    void should_throw_exception(){

        assertThrows(BookingException.class, () -> commandParser.parse("Bookticket Hari"),
                "Invalid Command Passed");

    }

    @Test
    @DisplayName("When command starts with Availability it should return Availability command")
    void should_return_availability_command() throws BookingException {
        Command command = commandParser.parse("Availability Show1234");

        Assertions.assertInstanceOf(AvailabilityCommand.class, command);
    }

    @DisplayName("{index} - {0} When Availability command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"Availability", "Availability SHOW12345 kari"})
    void should_throw_invalid_params_availability_command(String availabilityCmd) {

        assertThrows(BookingException.class, () -> commandParser.parse(availabilityCmd), "Invalid input");

    }

    @Test
    @DisplayName("When command starts with Book it should return Book command")
    void should_return_book_command() throws BookingException {
        Command command = commandParser.parse("Book SHOW12345 91234567 A1,A2,A3");

        Assertions.assertInstanceOf(BookCommand.class, command);
    }

    @DisplayName("{index} - {0} When Book command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"Book", "Book SHOW12345 kari"})
    void should_throw_invalid_params_book_command(String bookCmd) {

        assertThrows(BookingException.class, () -> commandParser.parse(bookCmd), "Invalid input");

    }

    @Test
    @DisplayName("When command starts with quit it should return Quit command")
    void should_return_quit_command() throws BookingException {
        Command command = commandParser.parse("Quit");

        Assertions.assertInstanceOf(QuitCommand.class, command);
    }

    @Test
    @DisplayName("When command starts with View it should return View command")
    void should_return_view_command() throws BookingException {
        Command command = commandParser.parse("View SHOW12345");

        Assertions.assertInstanceOf(ViewCommand.class, command);
    }

    @DisplayName("{index} - {0} When View command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"View", "View SHOW12345 kari"})
    void should_throw_invalid_params_view_command(String viewCmd) {

        assertThrows(BookingException.class, () -> commandParser.parse(viewCmd), "Invalid input");

    }

    @Test
    @DisplayName("When command starts with Cancel it should return Cancel command")
    void should_return_cancel_command() throws BookingException {
        Command command = commandParser.parse("Cancel T1234567 91234567");

        Assertions.assertInstanceOf(CancelCommand.class, command);
    }

    @DisplayName("{index} - {0} When Cancel command executed wrong params")
    @ParameterizedTest
    @ValueSource (strings = {"Cancel", "Cancel T1234567 91234567 12344"})
    void should_throw_invalid_params_cancel_command(String viewCmd) {

        assertThrows(BookingException.class, () -> commandParser.parse(viewCmd), "Invalid input");

    }
}