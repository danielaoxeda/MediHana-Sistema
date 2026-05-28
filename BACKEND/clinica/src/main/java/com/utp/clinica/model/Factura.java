package com.utp.clinica.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Getter
@Setter
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "estado_pago", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Integer estadoPago; // 0: Pendiente, 1: Pagada

    @Column(name = "creado_el", updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_el")
    private LocalDateTime actualizadoEl;

    @PrePersist
    protected void onCreate() {
        this.creadoEl = LocalDateTime.now();
        this.actualizadoEl = LocalDateTime.now();
        if (this.estadoPago == null) this.estadoPago = 0;
    }

    @PreUpdate
    protected void onUpdate() {
        this.actualizadoEl = LocalDateTime.now();
    }

    // Getters y Setters loombok
}