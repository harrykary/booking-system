package com.hari.kari.booking.Util;

import java.security.SecureRandom;

public class TicketIdGenerator {
    public static String generateId(){
        String currentTime = "T"+System.nanoTime();
        SecureRandom secureRandom = new SecureRandom();
        return  currentTime.concat(""+secureRandom.nextInt(100));
    }


}
