package com.hari.kari.booking.repo;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class MemUserRepoTest {
    @Autowired
    UserRepo userRepo;

    @Test
    void getDefaultUser() {
        Assertions.assertNotNull(userRepo.getUser("Hari"));
    }

    @Test
    void save() {
        User addtionalUser = new User("Kari", UserType.ADMIN);
        userRepo.save(addtionalUser);
        Assertions.assertNotNull(userRepo.getUser("Kari"));
    }
}