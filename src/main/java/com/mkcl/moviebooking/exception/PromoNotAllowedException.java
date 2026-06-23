package com.mkcl.moviebooking.exception;

public class PromoNotAllowedException
        extends RuntimeException {

    public PromoNotAllowedException(
            String message
    ) {
        super(message);
    }
}