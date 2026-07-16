package com.utp.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO para el registro de historiales médicos.
 * Contiene la información clínica que el médico registra tras una consulta.
 */
@Getter
@Setter
public class HistorialMedicoRequestDTO {

    /**
     * ID del paciente al que pertenece el historial.
     */
    @NotNull(message = "El ID del paciente es obligatorio.")
    private Integer idPaciente;

    /**
     * Diagnóstico emitido durante la consulta.
     */
    @NotBlank(message = "El diagnóstico es obligatorio.")
    @Size(max = 1000, message = "El diagnóstico no puede superar los 1000 caracteres.")
    private String diagnostico;

    /**
     * Tratamiento indicado para el paciente. Campo opcional.
     */
    @Size(max = 1000, message = "El tratamiento no puede superar los 1000 caracteres.")
    private String tratamiento;

    /**
     * Observaciones adicionales del médico. Campo opcional.
     */
    @Size(max = 1000, message = "Las observaciones no pueden superar los 1000 caracteres.")
    private String observaciones;

    /**
     * Fecha de la consulta. No puede ser una fecha futura.
     */
    @NotNull(message = "La fecha de la consulta es obligatoria.")
    @PastOrPresent(message = "La fecha de la consulta no puede ser futura.")
    private LocalDate fecha;
}
