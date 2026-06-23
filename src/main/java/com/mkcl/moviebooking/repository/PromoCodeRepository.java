package com.mkcl.moviebooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkcl.moviebooking.entity.PromoCode;

public interface PromoCodeRepository
        extends JpaRepository<PromoCode, Long> {

    Optional<PromoCode> findByCode(String code);
}