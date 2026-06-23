package com.mkcl.moviebooking.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mkcl.moviebooking.dto.BookingRequest;
import com.mkcl.moviebooking.dto.BookingResponse;
import com.mkcl.moviebooking.dto.CancelBookingResponse;
import com.mkcl.moviebooking.entity.Booking;
import com.mkcl.moviebooking.entity.BookingStatus;
import com.mkcl.moviebooking.entity.PromoCode;
import com.mkcl.moviebooking.entity.Show;
import com.mkcl.moviebooking.entity.User;
import com.mkcl.moviebooking.exception.BookingAlreadyCancelledException;
import com.mkcl.moviebooking.exception.PromoNotAllowedException;
import com.mkcl.moviebooking.exception.ResourceNotFoundException;
import com.mkcl.moviebooking.exception.SeatNotAvailableException;
import com.mkcl.moviebooking.exception.UnauthorizedBookingCancellationException;
import com.mkcl.moviebooking.repository.BookingRepository;
import com.mkcl.moviebooking.repository.PromoCodeRepository;
import com.mkcl.moviebooking.repository.ShowRepository;
import com.mkcl.moviebooking.repository.UserRepository;
import com.mkcl.moviebooking.service.BookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl
        implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final ShowRepository showRepository;
    
    private final PromoCodeRepository promoCodeRepository;

    @Override
    @Transactional
    public BookingResponse bookTicket(
            String email,
            BookingRequest request
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User Not Found"
                                )
                        );

        Show show =
                showRepository.findById(
                        request.getShowId()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Show Not Found"
                        )
                );

        if (show.getAvailableSeats() < request.getSeatCount()) {

            if (show.getAvailableSeats() == 0) {

                throw new SeatNotAvailableException(
                        "No seats available for this show"
                );
            }

            throw new SeatNotAvailableException(
                    "Only "
                            + show.getAvailableSeats()
                            + " seats are available"
            );
        }

        double totalAmount =
                show.getPrice()
                        * request.getSeatCount();

        Integer bookingCount =
                user.getBookingCount() == null
                        ? 0
                        : user.getBookingCount();

        Double totalSpent =
                user.getTotalSpent() == null
                        ? 0.0
                        : user.getTotalSpent();

        boolean eligibleForPromo =
                bookingCount > 5
                        || totalSpent > 1500;

                        if (request.getPromoCode() != null
                                && !request.getPromoCode().isBlank()) {

                            if (!eligibleForPromo) {

                                throw new PromoNotAllowedException(
                                        "User is not eligible for promo code"
                                );
                            }

                            PromoCode promo =
                                    promoCodeRepository
                                            .findByCode(
                                                    request.getPromoCode()
                                            )
                                            .orElseThrow(
                                                    () -> new PromoNotAllowedException(
                                                            "Invalid promo code"
                                                    )
                                            );

                            if (!promo.getActive()) {

                                throw new PromoNotAllowedException(
                                        "Promo code is inactive"
                                );
                            }

                            if (promo.getExpiryDate()
                                    .isBefore(LocalDate.now())) {

                                throw new PromoNotAllowedException(
                                        "Promo code has expired"
                                );
                            }

                            switch (promo.getDiscountType()) {

                                case "FREE_SEAT":

                                    if (request.getSeatCount() <= 1) {

                                        throw new PromoNotAllowedException(
                                                "FREE_SEAT requires minimum 2 seats"
                                        );
                                    }

                                    totalAmount =
                                            show.getPrice()
                                            * (request.getSeatCount() - 1);

                                    break;

                                case "FLAT":

                                    totalAmount =
                                            Math.max(
                                                    totalAmount
                                                    - promo.getDiscountValue(),
                                                    0
                                            );

                                    break;

                                default:

                                    throw new PromoNotAllowedException(
                                            "Unsupported promo type"
                                    );
                            }
                        }

        show.setAvailableSeats(
                show.getAvailableSeats()
                        - request.getSeatCount()
        );

        Booking booking =
                Booking.builder()
                        .user(user)
                        .show(show)
                        .seatCount(request.getSeatCount())
                        .amount(totalAmount)
                        .promoCode(request.getPromoCode())
                        .bookingTime(LocalDateTime.now())
                        .status(BookingStatus.CONFIRMED)
                        .build();

        bookingRepository.save(booking);

        user.setBookingCount(
                (user.getBookingCount() == null ? 0 : user.getBookingCount()) + 1
        );

        user.setTotalSpent(
                (user.getTotalSpent() == null ? 0.0 : user.getTotalSpent())
                + totalAmount
        );

        userRepository.save(user);

        showRepository.save(show);

        return BookingResponse.builder()
                .bookingId(
                        booking.getId()
                )
                .movieName(
                        show.getMovie()
                                .getTitle()
                )
                .seatCount(
                        booking.getSeatCount()
                )
                .totalAmount(totalAmount)
                .promoCode(
                        booking.getPromoCode()
                )
                .build();
    }

    @Override
    public List<BookingResponse>
    getMyBookings(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User Not Found"
                                )
                        );

        return bookingRepository
                .findByUser(user)
                .stream()
                .map(booking ->
                        BookingResponse.builder()
                                .bookingId(
                                        booking.getId()
                                )
                                .movieName(
                                        booking.getShow()
                                                .getMovie()
                                                .getTitle()
                                )
                                .seatCount(
                                        booking.getSeatCount()
                                )
                                .totalAmount(
                                        booking.getAmount()
                                )
                                .promoCode(
                                        booking.getPromoCode()
                                )
                                .build()
                )
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public CancelBookingResponse cancelBooking(
            Long bookingId,
            String email
    ) {

        Booking booking =
                bookingRepository
                        .findById(bookingId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Booking Not Found"
                                )
                        );

        if (!booking.getUser()
                .getEmail()
                .equals(email)) {

        	throw new UnauthorizedBookingCancellationException(
        	        "You can cancel only your bookings"
        	);
        }

        if (booking.getStatus()
                == BookingStatus.CANCELLED) {

        	throw new BookingAlreadyCancelledException(
        	        "Booking already cancelled"
        	);
        }

        booking.setStatus(
                BookingStatus.CANCELLED
        );

        Show show =
                booking.getShow();

        show.setAvailableSeats(
                show.getAvailableSeats()
                        + booking.getSeatCount()
        );

        User user =
                booking.getUser();

        user.setBookingCount(
                user.getBookingCount() - 1
        );

        user.setTotalSpent(
                user.getTotalSpent()
                        - booking.getAmount()
        );

        bookingRepository.save(booking);
        showRepository.save(show);
        userRepository.save(user);

        return CancelBookingResponse.builder()
                .bookingId(
                        booking.getId()
                )
                .status(
                        booking.getStatus().name()
                )
                .refundedSeats(
                        booking.getSeatCount()
                )
                .refundedAmount(
                        booking.getAmount()
                )
                .build();
    }
}