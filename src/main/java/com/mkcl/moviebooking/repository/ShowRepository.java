package com.mkcl.moviebooking.repository;

import com.mkcl.moviebooking.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository
        extends JpaRepository<Show, Long> {
}