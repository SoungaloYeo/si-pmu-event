package fr.pmu.domain;

import fr.pmu.config.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "race")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Race implements Serializable {

    @Schema(description = "Race id generated")
    @Id
    @SequenceGenerator(name = "race_id_sequence",sequenceName = "race_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "race_id_sequence")
    private Long id;

    @Schema(description = "Race date", requiredMode = Schema.RequiredMode.REQUIRED, example = "2023-04-04")
    @Column(name = "date")
    @FutureOrPresent(message = "Date error")
    private LocalDate date;

    @Schema(description = "Race name",requiredMode = Schema.RequiredMode.REQUIRED, example = "Course d'ete")
    @Column(name = "name")
    @NotBlank(message = "Name may not be empty")
    private String name;

    @Schema(description = "Race number is generated internally")
    @Column(name = "number", unique = true)
    @Builder.Default
    private String number = LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER);

    @Schema(description = "Starters associated at least 3", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 3, message = "size must be greater than 3")
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "race_starter",
            joinColumns = {@JoinColumn(name = "race_id")},
            inverseJoinColumns = {@JoinColumn(name = "starter_id")}
    )
    private Set<@Valid Starter> starters = new HashSet<>();


}
