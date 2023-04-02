package com.hari.kari.booking.config;

import com.hari.kari.booking.io.Listener;
import com.hari.kari.booking.io.CommandListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.jpmc.booking")
public class BookingSystemConfiguration {

    @Bean(name="commandListener")
    Listener getCommandListener(){
        return new CommandListener();
    }


}
