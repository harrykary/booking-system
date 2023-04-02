package com.hari.kari.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookingApplication implements CommandLineRunner {

    @Autowired
    BookingSystem bookingSystem;
    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(BookingApplication.class, args);
        log.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        log.info("EXECUTING : command line runner");
        bookingSystem.start();
    }
}
