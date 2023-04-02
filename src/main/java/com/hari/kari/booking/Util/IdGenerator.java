package com.hari.kari.booking.Util;

import java.security.SecureRandom;

public interface IdGenerator {

    String getPrefix();
    default String generateId(){
        String currentTime = getPrefix()+System.nanoTime();
        SecureRandom secureRandom = new SecureRandom();
        return  currentTime.concat(""+secureRandom.nextInt(100));
    }
}
