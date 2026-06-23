package com.mkcl.moviebooking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mkcl.moviebooking.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(
	        ResourceNotFoundException ex) {

	    ErrorResponse error =
	            ErrorResponse.builder()
	                    .message(ex.getMessage())
	                    .timestamp(LocalDateTime.now())
	                    .build();

	    return new ResponseEntity<>(
	            error,
	            HttpStatus.NOT_FOUND
	    );
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentials(
	        InvalidCredentialsException ex) {

	    ErrorResponse error =
	            ErrorResponse.builder()
	                    .message(ex.getMessage())
	                    .timestamp(LocalDateTime.now())
	                    .build();

	    return new ResponseEntity<>(
	            error,
	            HttpStatus.UNAUTHORIZED
	    );
	}
    
    @ExceptionHandler(
            SeatNotAvailableException.class
    )
    public ResponseEntity<ErrorResponse>
    handleSeatNotAvailable(
            SeatNotAvailableException ex
    ) {

        ErrorResponse error =
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(
                                LocalDateTime.now()
                        )
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(
            PromoNotAllowedException.class
    )
    public ResponseEntity<ErrorResponse>
    handlePromoNotAllowed(
            PromoNotAllowedException ex
    ) {

        ErrorResponse error =
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(
                                LocalDateTime.now()
                        )
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(
            BookingAlreadyCancelledException.class
    )
    public ResponseEntity<ErrorResponse>
    handleBookingAlreadyCancelled(
            BookingAlreadyCancelledException ex
    ) {

        ErrorResponse error =
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler(
            UnauthorizedBookingCancellationException.class
    )
    public ResponseEntity<ErrorResponse>
    handleUnauthorizedCancellation(
            UnauthorizedBookingCancellationException ex
    ) {

        ErrorResponse error =
                ErrorResponse.builder()
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build();

        return new ResponseEntity<>(
                error,
                HttpStatus.FORBIDDEN
        );
    }
    
}