package fr.pmu.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Table(name = "starter")
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Starter implements Serializable {

    @Schema(description = "Starter number generated")
    @Id
    @SequenceGenerator(
            name = "starter_id_sequence",
            sequenceName = "starter_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(generator = "starter_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long number;

    @Schema(description = "Starter name",requiredMode = Schema.RequiredMode.REQUIRED, example = "Cheval roux")
    @Column(name = "name")
    @NotBlank(message = "Starter name may not be empty")
    private String name;

}
