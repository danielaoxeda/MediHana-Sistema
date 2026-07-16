package com.utp.clinica.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para la emisión de facturas.
 * Contiene los datos requeridos para generar una factura asociada a una cita.
 */
@Getter
@Setter
public class FacturaRequestDTO {

    /**
     * ID de la cita a la que pertenece esta factura.
     */
    @NotNull(message = "El ID de la cita es obligatorio.")
    private Integer idCita;

    /**
     * Monto total de la factura. Debe ser mayor a 0.
     */
    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero.")
    private BigDecimal monto;

    /**
     * Fecha de emisión de la factura. No puede ser una fecha futura.
     */
    @NotNull(message = "La fecha de emisión es obligatoria.")
    @PastOrPresent(message = "La fecha de emisión no puede ser futura.")
    private LocalDate fechaEmision;
}
