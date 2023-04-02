package com.hari.kari.booking.exception;

public class BookingException extends Exception {

    public BookingException(String errMsg) {
        super(errMsg);
    }
    /*public BookingException(Throwable e){
        super(e);
    }

    public BookingException(String errMsg, Throwable e){
        super(errMsg,e);
    }*/
}
