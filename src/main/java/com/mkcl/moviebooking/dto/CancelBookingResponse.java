package com.mkcl.moviebooking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelBookingResponse {

    private Long bookingId;

    private String status;

    private Integer refundedSeats;

    private Double refundedAmount;
}