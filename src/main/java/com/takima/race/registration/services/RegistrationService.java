package com.takima.race.registration.services;

import com.takima.race.race.entities.Race;
import com.takima.race.race.repositories.RaceRepository;
import com.takima.race.registration.entities.Registration;
import com.takima.race.registration.repositories.RegistrationRepository;
import com.takima.race.runner.repositories.RunnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RunnerRepository runnerRepository;
    private final RaceRepository raceRepository;

    public RegistrationService(RegistrationRepository registrationRepository,
                               RunnerRepository runnerRepository,
                               RaceRepository raceRepository) {
        this.registrationRepository = registrationRepository;
        this.runnerRepository = runnerRepository;
        this.raceRepository = raceRepository;
    }

    public Registration register(Long raceId, Long runnerId) {
        // Runner existe ?
        runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Runner %s not found", runnerId)));

        // Race existe ?
        Race race = raceRepository.findById(raceId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Race %s not found", raceId)));

        // Déjà inscrit ?
        if (registrationRepository.existsByRunnerIdAndRaceId(runnerId, raceId)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Runner already registered to this race");
        }

        // Course complète ?
        long count = registrationRepository.countByRaceId(raceId);
        if (count >= race.getMaxParticipants()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Race is full");
        }

        Registration registration = new Registration();
        registration.setRunnerId(runnerId);
        registration.setRaceId(raceId);
        registration.setRegistrationDate(LocalDate.now());
        return registrationRepository.save(registration);
    }

    public List<Registration> getByRace(Long raceId) {
        raceRepository.findById(raceId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Race %s not found", raceId)));
        return registrationRepository.findByRaceId(raceId);
    }

    public List<Race> getRacesByRunner(Long runnerId) {
        runnerRepository.findById(runnerId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Runner %s not found", runnerId)));
        List<Long> raceIds = registrationRepository.findByRunnerId(runnerId)
            .stream().map(Registration::getRaceId).toList();
        return raceRepository.findAllById(raceIds);
    }

    public long countParticipants(Long raceId) {
        raceRepository.findById(raceId)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, String.format("Race %s not found", raceId)));
        return registrationRepository.countByRaceId(raceId);
    }
}