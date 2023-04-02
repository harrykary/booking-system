package com.hari.kari.booking.io;

import com.hari.kari.booking.command.CommandResult;
import com.hari.kari.booking.exception.BookingException;

public interface Listener {

    void start();
    void stop();
    CommandResult acceptCommand(String command) throws BookingException;
}
