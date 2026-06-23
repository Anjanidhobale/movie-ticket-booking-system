package com.mkcl.moviebooking.exception;

public class BookingAlreadyCancelledException
        extends RuntimeException {

    public BookingAlreadyCancelledException(
            String message
    ) {
        super(message);
    }
}