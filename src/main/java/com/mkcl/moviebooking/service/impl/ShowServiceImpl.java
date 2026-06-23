package com.mkcl.moviebooking.service.impl;

import com.mkcl.moviebooking.dto.ShowRequest;
import com.mkcl.moviebooking.entity.Movie;
import com.mkcl.moviebooking.entity.Show;
import com.mkcl.moviebooking.exception.ResourceNotFoundException;
import com.mkcl.moviebooking.repository.MovieRepository;
import com.mkcl.moviebooking.repository.ShowRepository;
import com.mkcl.moviebooking.service.ShowService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl
        implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;

    @Override
    public Show addShow(
            ShowRequest request) {

        Movie movie =
                movieRepository.findById(
                        request.getMovieId())
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Movie Not Found"
                                        )
                        );

        Show show = Show.builder()
                .movie(movie)
                .showTime(
                        request.getShowTime())
                .totalSeats(
                        request.getTotalSeats())
                .availableSeats(
                        request.getTotalSeats())
                .price(
                        request.getPrice())
                .build();

        return showRepository.save(show);
    }

    @Override
    public Show updateShow(
            Long showId,
            ShowRequest request) {

        Show show =
                showRepository.findById(showId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Show Not Found"
                                        )
                        );

        show.setShowTime(
                request.getShowTime());

        show.setPrice(
                request.getPrice());

        show.setTotalSeats(
                request.getTotalSeats());

        return showRepository.save(show);
    }

    @Override
    public void deleteShow(Long showId) {

        Show show =
                showRepository.findById(showId)
                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Show Not Found"
                                        )
                        );

        showRepository.delete(show);
    }

    @Override
    public Show getShow(Long showId) {

        return showRepository.findById(showId)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "Show Not Found"
                                )
                );
    }

    @Override
    public List<Show> getAllShows() {

        return showRepository.findAll();
    }
}