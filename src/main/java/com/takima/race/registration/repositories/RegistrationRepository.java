package com.takima.race.registration.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.takima.race.registration.entities.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    // Règle : Vérifier si le coureur est déjà inscrit (409)
    boolean existsByRunnerIdAndRaceId(Long runnerId, Long raceId);
    
    // Règle : Compter les inscrits pour ne pas dépasser le max (409)
    long countByRaceId(Long raceId);

    // Lister les participants d'une course
    List<Registration> findByRaceId(Long raceId);

    // Lister les courses d'un coureur
    List<Registration> findByRunnerId(Long runnerId);
}