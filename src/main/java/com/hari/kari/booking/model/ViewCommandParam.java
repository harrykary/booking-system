package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;

@Getter
public class ViewCommandParam implements CommandParam{

    private final String showId;
    public ViewCommandParam(String[] args) throws BookingException {
        validate(args);
        this.showId = args[1];

    }

    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 2){
            throw new BookingException("Invalid input");
        }
    }
}
