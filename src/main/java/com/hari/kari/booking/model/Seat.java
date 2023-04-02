package com.hari.kari.booking.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Seat {

   // private final char rowId;
    private final String seatId;
    @Setter
    @ToString.Exclude
    private String ticketId;

    public Seat( String seatId) {
        this.seatId = seatId;
    }
}
