package com.utp.clinica.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HistorialMedicoRequestDTO {
    private Integer idPaciente;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private LocalDate fecha;


}