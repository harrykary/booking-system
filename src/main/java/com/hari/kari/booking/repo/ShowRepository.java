package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.Show;

public interface ShowRepository {

    Show getShow(String showId);

    void save(Show show);

}
