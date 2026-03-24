package com.takima.race.race.controllers;

import com.takima.race.race.entities.Race;
import com.takima.race.race.services.RaceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping
    public List<Race> getAll(@RequestParam(required = false) String location) {
        return raceService.getAll(location);
    }

    @GetMapping("/{id}")
    public Race getById(@PathVariable Long id) {
        return raceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Race create(@RequestBody Race race) {
        return raceService.create(race);
    }
}