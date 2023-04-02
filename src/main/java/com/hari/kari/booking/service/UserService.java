package com.hari.kari.booking.service;

import com.hari.kari.booking.model.User;
import com.hari.kari.booking.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;


    public User getUser(String userName) {
        return userRepo.getUser(userName);
    }
}
