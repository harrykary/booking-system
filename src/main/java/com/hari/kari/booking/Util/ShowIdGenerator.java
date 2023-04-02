package com.hari.kari.booking.Util;

public class ShowIdGenerator implements IdGenerator{

    private String prefix;

    public ShowIdGenerator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }


}
