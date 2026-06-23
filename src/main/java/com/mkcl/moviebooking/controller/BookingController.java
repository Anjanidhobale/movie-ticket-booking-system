package com.mkcl.moviebooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkcl.moviebooking.dto.BookingRequest;
import com.mkcl.moviebooking.dto.BookingResponse;
import com.mkcl.moviebooking.dto.CancelBookingResponse;
import com.mkcl.moviebooking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse bookTicket(
            @RequestBody BookingRequest request,
            Authentication authentication
    ) {

        return bookingService.bookTicket(
                authentication.getName(),
                request
        );
    }

    @GetMapping("/my")
    public List<BookingResponse>
    getMyBookings(
            Authentication authentication
    ) {

        return bookingService.getMyBookings(
                authentication.getName()
        );
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<CancelBookingResponse>
    cancelBooking(
            @PathVariable Long bookingId,
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(
                        bookingId,
                        authentication.getName()
                )
        );
    }
}