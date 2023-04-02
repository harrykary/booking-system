package com.hari.kari.booking.configuration;

import com.hari.kari.booking.command.LineCommandListener;
import com.hari.kari.booking.io.Listener;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestBookingSystemConfiguration {

    @Bean(name="lineCommandListener")
    @Primary
    Listener getLineCommandListener(){
        return new LineCommandListener();
    }

}
