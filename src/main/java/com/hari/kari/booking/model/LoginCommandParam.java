package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;

@Getter
public class LoginCommandParam implements CommandParam{

    private final String userName;
    public LoginCommandParam(String[] args) throws BookingException {
        validate(args);
        this.userName = args[1];

    }

    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 2){
            throw new BookingException("Invalid input");
        }
    }
}
