package com.hari.kari.booking.model;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShowTest {

    Show show;
    @BeforeAll
    void setUp() {

        show = new Show("Show12345",10,5,3);
    }

    @Test
    void getNumRows() {
        Assertions.assertEquals(10, show.getNumRows());
    }

    @Test
    void getNumSeats() {
        Assertions.assertEquals(5, show.getNumSeats());
    }

    @Test
    void getMinsCancellationWindow() {
        Assertions.assertEquals(3, show.getMinsCancellationWindow());
    }

    @Test
    void getShowId() {
        Assertions.assertEquals("Show12345", show.getShowId());
    }


    @Test
    void testToString() {
        String showStr = show.toString();
        Assertions.assertTrue(showStr.contains("numRows=10"));
        Assertions.assertTrue(showStr.contains("numSeats=5"));
    }
}