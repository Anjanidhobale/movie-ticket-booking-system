package com.mkcl.moviebooking.controller;

import com.mkcl.moviebooking.dto.ShowRequest;
import com.mkcl.moviebooking.entity.Show;
import com.mkcl.moviebooking.service.ShowService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public Show addShow(
            @RequestBody ShowRequest request) {

        return showService.addShow(request);
    }

    @PutMapping("/{id}")
    public Show updateShow(
            @PathVariable Long id,
            @RequestBody ShowRequest request) {

        return showService.updateShow(
                id,
                request);
    }

    @DeleteMapping("/{id}")
    public String deleteShow(
            @PathVariable Long id) {

        showService.deleteShow(id);

        return "Show Deleted Successfully";
    }

    @GetMapping("/{id}")
    public Show getShow(
            @PathVariable Long id) {

        return showService.getShow(id);
    }

    @GetMapping
    public List<Show> getAllShows() {

        return showService.getAllShows();
    }
}