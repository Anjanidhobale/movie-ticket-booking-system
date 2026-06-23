package com.mkcl.moviebooking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequest {

    private Long movieId;
    private LocalDateTime showTime;
    private Integer totalSeats;
    private Double price;
}