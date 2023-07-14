package fr.pmu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pmu.domain.Race;
import fr.pmu.repository.RaceRepository;
import fr.pmu.service.RaceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {

    private final RaceRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public Race create(Race race) {
        log.debug("method create race beginning {}", race);
        repository.saveAndFlush(race);

        kafkaTemplate.send("course-XXL", objectMapper.writeValueAsString(race));
        return race;
    }

    @Override
    public List<Race> findAll() {
        log.debug("method findAll races beginning");
        return this.repository.findAll();
    }
}
