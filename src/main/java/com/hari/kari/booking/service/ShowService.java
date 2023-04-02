package com.hari.kari.booking.service;

import com.hari.kari.booking.Util.SeatGenerator;
import com.hari.kari.booking.model.Show;
import com.hari.kari.booking.repo.ShowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatGenerator seatGenerator;


    public String createShow(String showId, int rowNums, int seatNums, int cancellationWindow){
        Show show = new Show(showId, rowNums, seatNums, cancellationWindow);
        show.setSeats(seatGenerator.generateSeats(rowNums,seatNums));
        showRepository.save(show);
        return show.getShowId();
    }

    public Show getShow(String showNumber){
        return showRepository.getShow(showNumber);
    }

}
