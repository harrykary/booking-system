package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;

@Getter
public class BookCommandParam implements CommandParam {
    private final String showId;
    private final String phoneNumber;
    private final String[] seats;

    public BookCommandParam(String[] args) throws BookingException {
        validate(args);
        this.showId = args[1];
        this.phoneNumber = args[2];
        this.seats= args[3].split(",");

    }

    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 4){
            throw new BookingException("Invalid input");
        }
    }
}
