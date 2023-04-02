package com.hari.kari.booking.repo;

import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.Show;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestBookingSystemConfiguration.class)
public class MemShowRepositoryTest {

    @Autowired
    ShowRepository showRepository;

    @Test
    void save() {
        Show show = new Show("SHOW1234", 2,5,3);

        showRepository.save(show);

        Assertions.assertNotNull(showRepository.getShow("SHOW1234"));
    }

}