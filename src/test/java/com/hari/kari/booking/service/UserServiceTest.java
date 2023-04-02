package com.hari.kari.booking.service;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.User;
import com.hari.kari.booking.model.UserType;
import com.hari.kari.booking.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class UserServiceTest {
    @Mock
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @Test
    void should_return_user_upon_request() {
        Mockito.when(userRepo.getUser("Hari")).thenReturn(new User("Hari", UserType.ADMIN));

        User user = userService.getUser("Hari");

        Assertions.assertNotNull(user);
        Assertions.assertEquals("Hari", user.getUserName());
        Assertions.assertEquals(UserType.ADMIN, user.getUserType());
    }
}