package com.utp.clinica.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO para el registro de pagos de facturas.
 * Contiene los datos del pago que el paciente realiza para saldar una factura.
 */
@Getter
@Setter
public class PagoRequestDTO {

    /**
     * ID de la factura que se está pagando.
     */
    @NotNull(message = "El ID de la factura es obligatorio.")
    private Integer idFactura;

    /**
     * Monto pagado. Debe ser mayor a 0.
     */
    @NotNull(message = "El monto es obligatorio.")
    @DecimalMin(value = "0.01", message = "El monto del pago debe ser mayor a cero.")
    private BigDecimal monto;

    /**
     * Método de pago utilizado (ej. efectivo, tarjeta, transferencia).
     */
    @NotBlank(message = "El método de pago es obligatorio.")
    @Size(max = 50, message = "El método de pago no puede superar los 50 caracteres.")
    private String metodo;

    /**
     * Fecha en que se realizó el pago. No puede ser una fecha futura.
     */
    @NotNull(message = "La fecha del pago es obligatoria.")
    @PastOrPresent(message = "La fecha del pago no puede ser futura.")
    private LocalDate fecha;
}
