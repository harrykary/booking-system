package com.hari.kari.booking.model;

import com.hari.kari.booking.Util.TicketIdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
public class Ticket {

    String ticketId;
    @Setter
    String phone;
    @Setter
    String showId;
    List<Seat> seats = new ArrayList<>();

    LocalDateTime creationTime;


    public Ticket(){
        ticketId = TicketIdGenerator.generateId();
        creationTime = LocalDateTime.now();
    }

}
