package com.hari.kari.booking.repo;

import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemUserRepo implements UserRepo{

    private static Map<String, User> defaultUsers;

    static {
        defaultUsers = new HashMap<>();
        defaultUsers.put("Hari", new User("Hari", UserType.ADMIN ));
        defaultUsers.put("Kari", new User("Kari", UserType.BUYER ));
        defaultUsers.put("Harry", new User("Harry", UserType.BUYER ));
    }

    @Override
    public User getUser(String userName) {
        return defaultUsers.get(userName);
    }

    @Override
    public void save(User user) {
        defaultUsers.put(user.getUserName(), user);
    }


}
