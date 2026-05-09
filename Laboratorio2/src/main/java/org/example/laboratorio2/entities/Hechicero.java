package org.example.laboratorio2.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "hechiceros")
@Data
public class Hechicero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El grado es obligatorio")
    private String grade;

    @NotBlank(message = "La técnica maldita es obligatoria")
    private String cursedTechnique;

    @NotBlank(message = "La escuela es obligatoria")
    private String school;

    @NotNull(message = "La energía maldita no puede ser nula")
    private Double cursedEnergy;

    private Boolean isActive = true;
}
