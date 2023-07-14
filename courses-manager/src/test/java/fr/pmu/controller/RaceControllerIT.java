package fr.pmu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pmu.domain.Race;
import fr.pmu.domain.Starter;
import fr.pmu.exceptions.GlobalExceptionHandler;
import fr.pmu.repository.RaceRepository;
import fr.pmu.service.RaceService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@AutoConfigureMockMvc
class RaceControllerIT {

    public static final String BASE_URI = "/api/v1/races";

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private GlobalExceptionHandler globalControllerExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceRepository raceRepository;
    private MockMvc mockMvc;

    private Race race;
    private Starter starterOne;
    private Starter starterTwo;
    private Starter starterThree;

    @BeforeEach
    void setUp() {

        this.starterOne = Starter.builder().name("cheval blanc").build();
        this.starterTwo = Starter.builder().name("cheval roux").build();
        this.starterThree = Starter.builder().name("cheval gris").build();


        // initialize mockMvc
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RaceController(raceService))
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Test
    void given_right_parameter_creation_should_succeed() throws Exception {

        this.initializeRaceWithStarter(Set.of(starterOne, starterTwo, starterThree));

        Assertions.assertThat(this.raceRepository.findAll()).isEmpty();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URI)
                .content(objectMapper.writeValueAsString(this.race))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated());

        var races = this.raceRepository.findAll();
        Assertions.assertThat(races)
                .isNotEmpty()
                .hasSize(1)
                .first()
                .extracting(Race::getId, Race::getName)
                .contains(1L, race.getName());

        var  starters  = races.stream().findAny().get().getStarters();
        Assertions.assertThat(starters)
                .isNotEmpty()
                .hasSize(3)
                .extracting(Starter::getName)
                .containsExactlyInAnyOrder(starterOne.getName(), starterTwo.getName(), starterThree.getName());
    }

    @Test
    void given_less_than_3_starters_should_failed() throws Exception {

        this.initializeRaceWithStarter((Set.of(starterOne, starterTwo)));

    Assertions.assertThat(this.raceRepository.findAll()).isEmpty();

    RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post(BASE_URI)
            .content(objectMapper.writeValueAsString(this.race))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .characterEncoding("utf-8");

    mockMvc.perform(requestBuilder)
            .andExpect(status().isBadRequest());
    }

    @Test
    void given_earlier_date_should_failed() throws Exception {
        this.initializeRaceWithStarter(Set.of(starterOne, starterTwo, starterThree));
        this.race.setDate(LocalDate.now().minusDays(1));

        Assertions.assertThat(this.raceRepository.findAll()).isEmpty();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(BASE_URI)
                .content(objectMapper.writeValueAsString(this.race))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    private void initializeRaceWithStarter(Set<Starter> starters) {
        this.race  = Race.builder()
                .name("Course de l'ete")
                .date(LocalDate.now())
                .starters(starters)
                .build();
    }
}