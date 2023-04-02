package com.hari.kari.booking;

import com.hari.kari.booking.io.Listener;
import com.hari.kari.booking.exception.BookingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingSystem {

    @Autowired
    Listener listener;


    public void start(){
        listener.start();
    }

    public void stop() throws BookingException {
        listener.acceptCommand("logout");
        listener.stop();
    }
}
