package com.utp.clinica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "administradores")
@Getter
@Setter
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 15, unique = true)
    private String dni;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String telefono;

    @Column(length = 50)
    private String rol;

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

    // Getters y Setters loombok
}