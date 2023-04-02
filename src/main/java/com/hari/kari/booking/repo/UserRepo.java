package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.User;

public interface UserRepo {

    User getUser(String userName);

    void save(User user);

}
