package com.hari.kari.booking.Util;

import com.hari.kari.booking.model.Seat;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeatGenerator {

    public List<Seat> generateSeats(int rowNumber, int seatNumber){
        List<Seat> seats = new ArrayList<>();

        // Given ASCII Value of A=65 to Z=90
        for(int i=65; i<(65+rowNumber); i++){
            for (int j = 1; j <= seatNumber; j++) {
                Seat seat = new Seat(String.valueOf((char) i) + j);
                seats.add(seat);
            }

        }
        return seats;
    }
}
