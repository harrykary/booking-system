package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;

@Getter
public class CancelCommandParam implements CommandParam{

    private final String ticketId;
    private final String phone;

    public CancelCommandParam(String[] args) throws BookingException {
       validate(args);
        this.ticketId = args[1];
        this.phone = args[2];

    }

    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 3){
            throw new BookingException("Invalid input");
        }
    }
}
