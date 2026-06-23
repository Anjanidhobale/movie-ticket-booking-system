package com.mkcl.moviebooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="show_id")
    private Show show;

    private Integer seatCount;

    private Double amount;

    private String promoCode;

    private LocalDateTime bookingTime;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}