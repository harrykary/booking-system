package com.hari.kari.booking.model;


import com.hari.kari.booking.exception.BookingException;

public interface CommandParam {

    void validate(String[] args) throws BookingException;


}
