package com.utp.clinica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultorios")
@Getter
@Setter
public class Consultorio {

    @Id
    private Integer id; // Número del consultorio

    @Column(nullable = false)
    private Integer piso;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Integer estado;

    @Column(name = "creado_el", updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;

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


}