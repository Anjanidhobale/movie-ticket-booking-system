package com.mkcl.moviebooking.service;

import java.util.List;

import com.mkcl.moviebooking.dto.BookingRequest;
import com.mkcl.moviebooking.dto.BookingResponse;
import com.mkcl.moviebooking.dto.CancelBookingResponse;

public interface BookingService {

    BookingResponse bookTicket(
            String email,
            BookingRequest request
    );

    List<BookingResponse> getMyBookings(
            String email
    );
    
    CancelBookingResponse cancelBooking(
            Long bookingId,
            String email
    );
}