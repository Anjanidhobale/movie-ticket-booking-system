package com.mkcl.moviebooking.service.impl;

import com.mkcl.moviebooking.dto.MovieRequest;
import com.mkcl.moviebooking.entity.Movie;
import com.mkcl.moviebooking.exception.ResourceNotFoundException;
import com.mkcl.moviebooking.repository.MovieRepository;
import com.mkcl.moviebooking.service.MovieService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl
        implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Movie addMovie(
            MovieRequest request) {

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .description(request.getDescription())
                .build();

        return movieRepository.save(movie);
    }

    @Override
    public Movie updateMovie(
            Long movieId,
            MovieRequest request) {

        Movie movie =
                movieRepository.findById(movieId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Movie Not Found"
                                        )
                        );

        movie.setTitle(
                request.getTitle());

        movie.setLanguage(
                request.getLanguage());

        movie.setDuration(
                request.getDuration());

        movie.setGenre(
                request.getGenre());

        movie.setDescription(
                request.getDescription());

        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(
            Long movieId) {

        Movie movie =
                movieRepository.findById(movieId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Movie Not Found"
                                        )
                        );

        movieRepository.delete(movie);
    }

    @Override
    public Movie getMovie(Long movieId) {

        return movieRepository.findById(movieId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Movie Not Found"
                                )
                );
    }

    @Override
    public List<Movie> getAllMovies() {

        return movieRepository.findAll();
    }
}