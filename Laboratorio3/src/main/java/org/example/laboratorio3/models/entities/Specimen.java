package org.example.laboratorio3.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "specimens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specimen {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "The specimen name cannot be empty.")
    @Column(name = "name", nullable = false, unique = true) // El nombre es obligatorio y debe ser único en Hyrule
    private String name;

    @NotBlank(message = "The region of Hyrule must be specified.")
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull(message = "Danger level is required.")
    @Min(value = 1, message = "Danger level must be at least 1.")
    @Max(value = 5, message = "Danger level cannot exceed 5.")
    @Column(name = "danger_level", nullable = false)
    private Integer dangerLevel;

    @NotNull(message = "You must specify if the specimen is friendly.")
    @Column(name = "is_friendly", nullable = false)
    private Boolean isFriendly;
}
