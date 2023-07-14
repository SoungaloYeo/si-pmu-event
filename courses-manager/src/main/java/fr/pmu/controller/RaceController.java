package fr.pmu.controller;

import fr.pmu.controller.annotations.ApiErrorResponses;
import fr.pmu.controller.utils.HeaderUtil;
import fr.pmu.domain.Race;
import fr.pmu.service.RaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "RACE API MANAGEMENT")
@RequestMapping("/api/v1")
public class RaceController {

    private final RaceService raceService;

    /**
     * {@code POST  /races} : Create a new race.
     *
     * @param race the race to create.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Operation(summary = "Save a new Race")
    @ApiErrorResponses
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Race created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Race.class)) })})
    @PostMapping("/races")
    public ResponseEntity<Race> createRace(@Valid @RequestBody Race race) throws URISyntaxException {
        log.debug("===> REST request to save Race : {}", race);
        if (race.getId() != null) {
            throw new RuntimeException("id must be null to create new race");
        }
        var result = raceService.create(race);
        return ResponseEntity
                .created(new URI("/api/v1/races"+result.getId()))
                .headers(HeaderUtil.createHeaders("raceCreatedResponse"))
                .body(result);
    }

    /**
     * {@code GET  /races} : get all the races.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of races in body.
     */
    @Operation(summary = "Retrieve all Races")
    @ApiErrorResponses
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "20O", description = "All races",
                    content = {
                            @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Race.class)))
            })
    })
    @GetMapping("/races")
    public ResponseEntity<List<Race>> getAllRaces() {
        log.debug("REST request to retrieve Races");
        var result = raceService.findAll();

       return ResponseEntity
                .ok()
                .headers(HeaderUtil.createHeaders("getAllRaces"))
                .body(result);
    }
}
