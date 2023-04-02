package com.hari.kari.booking.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {

    User user;

    @BeforeAll
    void setUp(){
        user = new User("Hari", UserType.ADMIN);
    }
    @Test
    void getUserName() {
        Assertions.assertEquals("Hari", user.getUserName());
    }

    @Test
    void getUserType() {
        Assertions.assertEquals(UserType.ADMIN, user.getUserType());
    }

    @Test
    void testToString() {
        String userStr = user.toString();
        System.out.println(userStr);
        Assertions.assertTrue(userStr.contains("userName='Hari'"));
        Assertions.assertTrue(userStr.contains("userType=ADMIN"));
    }
}