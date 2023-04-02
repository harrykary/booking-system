package com.hari.kari.booking.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Show {

    private final String showId;
    private final int numRows;
    private final int numSeats;

    private final int minsCancellationWindow;

    @Setter
    private List<Seat> seats;

    public Show( String showId, int numRows, int numSeats, int minsCancellationWindow) {
        this.showId = showId;
        this.numRows = numRows;
        this.numSeats = numSeats;
        this.minsCancellationWindow = minsCancellationWindow;
    }



    @Override
    public String toString() {
        return "Show{" +
                "numRows=" + numRows +
                ", numSeats=" + numSeats +
                ", minsCancellationWindow=" + minsCancellationWindow +
                ", seats=" + seats +
                ", showId=" + showId +
                '}';
    }
}
