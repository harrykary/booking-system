package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.Show;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemShowRepository  implements ShowRepository {

    private static final Map<String, Show> shows;

    static {
        shows = new HashMap<>();
    }


    public Show getShow(String showId){
        return shows.get(showId);
    }

    public void save(Show show){
        shows.put(show.getShowId(), show);
    }

}
