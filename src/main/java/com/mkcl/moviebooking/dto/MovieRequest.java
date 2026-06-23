package com.mkcl.moviebooking.dto;

import lombok.Data;

@Data
public class MovieRequest {

    private String title;
    private String language;
    private Integer duration;
    private String genre;
    private String description;
}