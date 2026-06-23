package com.mkcl.moviebooking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mkcl.moviebooking.dto.MovieRequest;
import com.mkcl.moviebooking.entity.Movie;
import com.mkcl.moviebooking.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(name = "Movie APIs")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Add New Movie"
    )
    @PostMapping
    public Movie addMovie(
            @RequestBody MovieRequest request) {

        return movieService.addMovie(
                request);
    }

    @Operation(
            summary = "Update Movie"
    )
    @PutMapping("/{id}")
    public Movie updateMovie(
            @PathVariable Long id,
            @RequestBody MovieRequest request) {

        return movieService.updateMovie(
                id,
                request);
    }

    @Operation(
            summary = "Delete Movie"
    )
    @DeleteMapping("/{id}")
    public String deleteMovie(
            @PathVariable Long id) {

        movieService.deleteMovie(id);

        return "Movie Deleted Successfully";
    }

    @Operation(
            summary = "Get Movie"
    )
    @GetMapping("/{id}")
    public Movie getMovie(
            @PathVariable Long id) {

        return movieService.getMovie(id);
    }

    @Operation(
            summary = "Get All Movies"
    )
    @GetMapping
    public List<Movie> getAllMovies() {

        return movieService.getAllMovies();
    }
}