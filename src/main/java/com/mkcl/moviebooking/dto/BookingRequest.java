package com.mkcl.moviebooking.dto;

import lombok.Data;

@Data
public class BookingRequest {

    private Long showId;
    private Integer seatCount;
    private String promoCode;
}