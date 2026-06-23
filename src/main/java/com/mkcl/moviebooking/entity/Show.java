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
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    private LocalDateTime showTime;

    private Integer totalSeats;

    private Integer availableSeats;

    private Double price;

    @Version
    private Long version;
}