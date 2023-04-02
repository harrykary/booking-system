package com.hari.kari.booking.service;

import com.hari.kari.booking.Util.SeatGenerator;
import com.hari.kari.booking.configuration.TestBookingSystemConfiguration;
import com.hari.kari.booking.model.Show;
import com.hari.kari.booking.repo.ShowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestBookingSystemConfiguration.class)
public class ShowServiceTest {

    @Mock
    ShowRepository showRepository;

    @Mock
    SeatGenerator seatGenerator;

    @InjectMocks
    ShowService showService;


    @Test
    void createShow() {
        Mockito.doNothing().when(showRepository).save(Mockito.any(Show.class));
        String showNumber = showService.createShow("SHOW1234", 3,5,3);
        Assertions.assertNotNull(showNumber);
        Assertions.assertEquals("SHOW1234", showNumber);
    }

    @Test
    void getShow() {
        Show show = new Show("SHOW1234", 3,5,3);
        Mockito.when(showRepository.getShow("SHOW1234")).thenReturn(show);
        String showNumber = showService.createShow("SHOW1234", 3,5,3);
        Assertions.assertNotNull(showNumber);
        Assertions.assertEquals("SHOW1234", showNumber);
    }
}