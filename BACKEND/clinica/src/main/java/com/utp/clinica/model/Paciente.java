package com.utp.clinica.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Clave primaria fija 'id' según el estándar [cite: 304]

    @Column(nullable = false, length = 100)
    private String nombre; // [cite: 215]

    @Column(nullable = false, length = 15, unique = true)
    private String dni; // [cite: 215]

    @Column(length = 20)
    private String telefono; // [cite: 215]

    @Column(length = 100)
    private String correo; // [cite: 216]

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer estado; // 1 = Activo, 0 = Inactivo [cite: 305]

    @Column(name = "creado_el", updatable = false)
    private LocalDateTime creadoEl; // [cite: 305]

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl; // [cite: 305]

    @PrePersist
    protected void onCreate() {
        this.creadoEl = LocalDateTime.now();
        this.actualizadoEl = LocalDateTime.now();
        if (this.estado == null) this.estado = 1;
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEl = LocalDateTime.now();
    }

    // Getters, Setters y Constructores loombok
}