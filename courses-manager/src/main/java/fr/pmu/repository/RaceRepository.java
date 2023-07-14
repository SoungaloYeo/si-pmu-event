package fr.pmu.repository;

import fr.pmu.domain.Race;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceRepository extends JpaRepository<Race, Long> {
}
