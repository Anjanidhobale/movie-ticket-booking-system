package com.mkcl.moviebooking.service;

import com.mkcl.moviebooking.dto.MovieRequest;
import com.mkcl.moviebooking.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie addMovie(MovieRequest request);

    Movie updateMovie(
            Long movieId,
            MovieRequest request
    );

    void deleteMovie(Long movieId);

    Movie getMovie(Long movieId);

    List<Movie> getAllMovies();
}