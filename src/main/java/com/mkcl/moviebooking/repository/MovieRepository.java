package com.mkcl.moviebooking.repository;

import com.mkcl.moviebooking.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository
        extends JpaRepository<Movie, Long> {
}