package com.mkcl.moviebooking.exception;

public class UnauthorizedBookingCancellationException
        extends RuntimeException {

    public UnauthorizedBookingCancellationException(
            String message
    ) {
        super(message);
    }
}