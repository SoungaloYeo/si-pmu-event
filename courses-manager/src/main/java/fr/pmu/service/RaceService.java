package fr.pmu.service;

import fr.pmu.domain.Race;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RaceService {

    /**
     * create Race and Starter related
     * @return
     */
    Race create(@NotNull Race race);

    /**
     * retrieve all Pages Races
     * @return
     */
    List<Race> findAll();
}
