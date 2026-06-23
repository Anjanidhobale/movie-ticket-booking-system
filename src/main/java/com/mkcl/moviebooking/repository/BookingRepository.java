package com.mkcl.moviebooking.repository;

import com.mkcl.moviebooking.entity.Booking;
import com.mkcl.moviebooking.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);
}