package com.mkcl.moviebooking.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long bookingId;

    private String movieName;

    private Integer seatCount;

    private Double totalAmount;

    private String promoCode;
}