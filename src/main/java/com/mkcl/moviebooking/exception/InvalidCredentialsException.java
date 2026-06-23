package com.mkcl.moviebooking.exception;

public class InvalidCredentialsException
        extends RuntimeException {

    public InvalidCredentialsException(
            String message
    ) {
        super(message);
    }
}