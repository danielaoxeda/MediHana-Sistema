package com.utp.clinica.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaRequestDTO {

    private Integer idPaciente;
    private Integer idMedico;
    private Integer idConsultorio;
    private Integer idEspecialidad;
    private LocalDate fecha;
    private LocalTime hora;

    // Getters y Setters cambiar a anotaciones , si alguien lo ve xd
    public Integer getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Integer idPaciente) { this.idPaciente = idPaciente; }

    public Integer getIdMedico() { return idMedico; }
    public void setIdMedico(Integer idMedico) { this.idMedico = idMedico; }

    public Integer getIdConsultorio() { return idConsultorio; }
    public void setIdConsultorio(Integer idConsultorio) { this.idConsultorio = idConsultorio; }

    public Integer getIdEspecialidad() { return idEspecialidad; }
    public void setIdEspecialidad(Integer idEspecialidad) { this.idEspecialidad = idEspecialidad; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
}