package com.hari.kari.booking;

import com.hari.kari.booking.command.CommandResult;
import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.exception.BookingException;
import com.hari.kari.booking.io.Listener;
import com.hari.kari.booking.model.Show;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserContext;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.service.ShowService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class BookingSystemAcceptanceTest {

    @SpyBean
    @Qualifier("lineCommandListener")
    Listener listener;
    @Autowired
    private BookingSystem bookingSystem;

    @Autowired
    private ShowService showService;

    @AfterEach
    void tearDown() throws BookingException {
        bookingSystem.stop();
    }

    @Test
    @DisplayName("User context should be null at the start of the app")
    public void should_have_empty_context(){

        bookingSystem.start();

        UserContext currentUserContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNull(currentUserContext);
    }


    @Test
    @DisplayName("Admin user should be logged in")
    public void should_be_able_to_login() throws BookingException {

        // given
        bookingSystem.start();
        //when
        CommandResult result = listener.acceptCommand("Login Hari");
        //then
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
        UserContext currentUserContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNotNull(currentUserContext);
        Assertions.assertNotNull(currentUserContext.getUser());

    }

    static Stream<User> generateUser() {
        return Stream.of(
                new User("Hari", UserType.ADMIN)
                ,new User("Kari", UserType.BUYER)
        );
    }
    @ParameterizedTest
    @DisplayName("User type should be identified correctly")
    @MethodSource("generateUser")
    public void should_have_user_type_added_in_context(User user) throws BookingException {

        // given
        bookingSystem.start();
        String loginCommand = "Login "+ user.getUserName();
        //when
        CommandResult result =listener.acceptCommand(loginCommand);
        //then
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
        UserContext currentUserContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNotNull(currentUserContext);
        Assertions.assertEquals(user.getUserType(), currentUserContext.getUser().getUserType());
    }

    @Test
    @DisplayName("User should logout and context should be null")
    public void should_be_able_to_logout() throws BookingException {

        // given
        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        UserContext currentUserContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNotNull(currentUserContext);
        //when
        CommandResult result= listener.acceptCommand("logout");
        //then
        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
        currentUserContext = UserContext.UserContextManager.getCurrentUserContext();
        Assertions.assertNull(currentUserContext);
    }
    @Test
    @DisplayName("Admin should create a show successfully")
    public void admin_should_create_show() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");

        CommandResult result=listener.acceptCommand("setup SHOW12345 3 5 2");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);
        Show show = showService.getShow("SHOW12345");
        Assertions.assertEquals(3, show.getNumRows());
        Assertions.assertEquals(5, show.getNumSeats());
        Assertions.assertEquals(2, show.getMinsCancellationWindow());
        Assertions.assertEquals(15, show.getSeats().size());
    }

    @Test
    @DisplayName("Admin should not create a show if rows are invalid")
    public void admin_should_not_create_show_invalid_rows() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");

        CommandResult result=listener.acceptCommand("setup SHOW12345 27 5 2");
        System.out.println(result);

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
        Assertions.assertEquals("Either rows or seats are out of range", result.getMessage());
    }

    @Test
    @DisplayName("Admin should not create a show if seats are invalid")
    public void admin_should_not_create_show_invalid_seats() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");

        CommandResult result=listener.acceptCommand("setup SHOW12345 10 11 2");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
        Assertions.assertEquals("Either rows or seats are out of range", result.getMessage());
    }

    @Test
    @DisplayName("Admin should not create a show if rows are invalid")
    public void admin_should_not_create_show_non_number() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");

        CommandResult result=listener.acceptCommand("setup SHOW12345 abc 5 2");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
        Assertions.assertEquals("Invalid rowNumber or seatNumber or cancellation Window", result.getMessage());
    }


    @Test
    @DisplayName("Buyer Should get error if unavailable show is passed")
    public void buyer_should_get_error_available_seats() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Kari");

        CommandResult result=listener.acceptCommand("Availability SHOW123456");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);
        Assertions.assertEquals("Show not found", result.getMessage());

    }
    @Test
    @DisplayName("Buyer Should be able to see all available seats")
    public void buyer_should_see_available_seats() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");

        CommandResult result=listener.acceptCommand("Availability SHOW12345");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);

    }

    @Test
    @DisplayName("Buyer Should book available seats")
    public void buyer_should_book_available_seats() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");

        CommandResult result=listener.acceptCommand("Book SHOW12345 91234567 A1,A2,A3");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);

    }

    @Test
    @DisplayName("Should failure complaining about phone")
    public void same_phone_used_to_book_available_seats() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");
        listener.acceptCommand("Book SHOW12345 91234567 A1,A2,A3");
        listener.acceptCommand("Logout");

        listener.acceptCommand("Login Harry");
        CommandResult result= listener.acceptCommand("Book SHOW12345 91234567 B1,B2");
        System.out.println(result);

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);

        Assertions.assertEquals("Only one booking per phone number is allowed per show", result.getMessage());

    }

    @Test
    @DisplayName("Amin Should be able to details about the Show and its tickets")
    public void admin_should_see_booking_details() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");
        listener.acceptCommand("Book SHOW12345 91234567 A1,A2,A3");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Harry");
        listener.acceptCommand("Book SHOW12345 90000000 B1,B2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Hari");

        CommandResult result=listener.acceptCommand("view SHOW12345");

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);

    }

    @Test
    @DisplayName("Buyer should be able to cancel his booked ticket")
    public void buyer_cancel_ticket() throws BookingException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 2");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Harry");
        listener.acceptCommand("Book SHOW12345 90000000 B1,B2,B3");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");
        CommandResult bookTicket = listener.acceptCommand("Book SHOW12345 91234567 A1,A2,A3");

        String cancelCommand="Cancel " + bookTicket.getMessage() + " 91234567";
        CommandResult result = listener.acceptCommand(cancelCommand);

        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Hari");
        CommandResult availableSeats=listener.acceptCommand("view SHOW12345");
        System.out.println(availableSeats);

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.SUCCESS, result.commandStatus);

    }

    @Test
    @DisplayName("Buyer should not be able to cancel his booked ticket if cancellation window passed")
    public void buyer_should_not_cancel_ticket() throws BookingException, InterruptedException {

        bookingSystem.start();
        listener.acceptCommand("Login Hari");
        listener.acceptCommand("setup SHOW12345 3 5 1");
        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Kari");
        CommandResult bookTicket = listener.acceptCommand("Book SHOW12345 91234567 A1,A2,A3");
        Thread.sleep(61000);

        String cancelCommand="Cancel " + bookTicket.getMessage() + " 91234567";
        CommandResult result = listener.acceptCommand(cancelCommand);

        listener.acceptCommand("Logout");
        listener.acceptCommand("Login Hari");
        CommandResult availableSeats=listener.acceptCommand("view SHOW12345");
        System.out.println(availableSeats);

        Assertions.assertEquals(CommandResult.COMMAND_STATUS.FAILURE, result.commandStatus);

    }

}
