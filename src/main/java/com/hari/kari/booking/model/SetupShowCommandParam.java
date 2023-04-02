package com.hari.kari.booking.model;

import com.hari.kari.booking.exception.BookingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SetupShowCommandParam implements CommandParam{

    private String showId;
    private int numOfRows;
    private int numOfSeats;
    private int cancelWindowMinutes;
    public SetupShowCommandParam(String[] args) throws BookingException {
        validate(args);
    }


    @Override
    public void validate(String[] args) throws BookingException {
        if(args.length != 5){
            log.warn("Args received from command {}", args);
            throw new BookingException("Invalid input");
        }
        try {
            this.showId = args[1];
            this.numOfRows = Integer.parseInt(args[2]);
            this.numOfSeats = Integer.parseInt(args[3]);
            this.cancelWindowMinutes = Integer.parseInt(args[4]);
        } catch (NumberFormatException exception){
            throw new BookingException("Invalid rowNumber or seatNumber or cancellation Window");
        }

        if(this.numOfRows > 26 || this.numOfSeats >10) {
            throw new BookingException("Either rows or seats are out of range");
        }
    }
}
