package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;

@Getter
public class EmptyParam implements CommandParam{

    public EmptyParam(String[] args) throws BookingException {
       validate(args);
    }

    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 1){
            throw new BookingException("Invalid input");
        }
    }
}
