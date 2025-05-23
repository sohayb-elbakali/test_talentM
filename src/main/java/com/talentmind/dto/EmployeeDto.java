package com.talentmind.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    /* Le champ id est en lecture seule et ne doit pas être fourni lors de la création.
     Pour cela, j'utilise l'annotation
     @Schema(accessMode = Schema.AccessMode.READ_ONLY) sur le champ id de DTO */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String poste;
    private Double salaire;
    private Long departmentId;
}