package com.takima.race.registration.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.takima.race.race.entities.Race;
import com.takima.race.registration.entities.Registration;
import com.takima.race.registration.services.RegistrationService;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/races/{raceId}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public Registration register(@PathVariable Long raceId,
                                 @RequestBody Map<String, Long> body) {
        return registrationService.register(raceId, body.get("runnerId"));
    }

    @GetMapping("/races/{raceId}/registrations")
    public List<Registration> getParticipants(@PathVariable Long raceId) {
        return registrationService.getByRace(raceId);
    }

    @GetMapping("/runners/{runnerId}/races")
    public List<Race> getRacesByRunner(@PathVariable Long runnerId) {
        return registrationService.getRacesByRunner(runnerId);
    }

    @GetMapping("/races/{raceId}/participants/count")
    public Map<String, Long> countParticipants(@PathVariable Long raceId) {
        return Map.of("count", registrationService.countParticipants(raceId));
    }
}