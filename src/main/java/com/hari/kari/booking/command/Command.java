package com.hari.kari.booking.command;

import com.hari.kari.booking.model.CommandParam;
import com.hari.kari.booking.exception.BookingException;

public interface Command<T extends CommandParam>{

    CommandResult execute() throws BookingException;

    void setParams(T t);

    T getParams();


}
