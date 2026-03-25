package com.takima.race.runner.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.takima.race.runner.entities.Runner;
import com.takima.race.runner.repositories.RunnerRepository;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;

    public RunnerService(RunnerRepository runnerRepository) {
        this.runnerRepository = runnerRepository;
    }

    public List<Runner> getAll() {
        return runnerRepository.findAll();
    }

    public Runner getById(Long id) {
        return runnerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Runner %s not found", id))
        );
    }

    public Runner create(Runner runner) {
        // Validation de l'email : doit contenir un @
        if (runner.getEmail() == null || !runner.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email address");
        }
        return runnerRepository.save(runner);
    }

    public Runner update(Long id, Runner updatedRunner) {
        // On vérifie que le coureur existe (lève 404 sinon)
        Runner existing = getById(id);

        // Validation de l'email
        if (updatedRunner.getEmail() == null || !updatedRunner.getEmail().contains("@")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email address");
        }

        // On met à jour les champs
        existing.setFirstName(updatedRunner.getFirstName());
        existing.setLastName(updatedRunner.getLastName());
        existing.setEmail(updatedRunner.getEmail());
        existing.setAge(updatedRunner.getAge());

        return runnerRepository.save(existing);
    }

    public void delete(Long id) {
        // On vérifie que le coureur existe (lève 404 sinon)
        getById(id);
        runnerRepository.deleteById(id);
    }

    public void deleteById(Long id) {
        getById(id); 
        runnerRepository.deleteById(id);
    }
}