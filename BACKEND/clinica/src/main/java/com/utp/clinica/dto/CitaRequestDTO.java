package com.utp.clinica.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para la creación y reprogramación de citas médicas.
 * Contiene los datos necesarios para registrar una cita en el sistema.
 */
@Getter
@Setter
public class CitaRequestDTO {

    /**
     * ID del paciente que solicita la cita.
     */
    @NotNull(message = "El ID del paciente es obligatorio.")
    private Integer idPaciente;

    /**
     * ID del médico asignado a la cita.
     */
    @NotNull(message = "El ID del médico es obligatorio.")
    private Integer idMedico;

    /**
     * ID del consultorio donde se realizará la cita.
     */
    @NotNull(message = "El ID del consultorio es obligatorio.")
    private Integer idConsultorio;

    /**
     * ID de la especialidad médica de la cita.
     */
    @NotNull(message = "El ID de la especialidad es obligatorio.")
    private Integer idEspecialidad;

    /**
     * Fecha de la cita. No puede ser una fecha pasada.
     */
    @NotNull(message = "La fecha de la cita es obligatoria.")
    @FutureOrPresent(message = "La fecha de la cita no puede ser en el pasado.")
    private LocalDate fecha;

    /**
     * Hora de la cita.
     */
    @NotNull(message = "La hora de la cita es obligatoria.")
    private LocalTime hora;
}
