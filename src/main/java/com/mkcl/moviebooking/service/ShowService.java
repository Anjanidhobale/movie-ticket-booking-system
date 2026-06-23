package com.mkcl.moviebooking.service;

import com.mkcl.moviebooking.dto.ShowRequest;
import com.mkcl.moviebooking.entity.Show;

import java.util.List;

public interface ShowService {

    Show addShow(ShowRequest request);

    Show updateShow(
            Long showId,
            ShowRequest request
    );

    void deleteShow(Long showId);

    Show getShow(Long showId);

    List<Show> getAllShows();
}