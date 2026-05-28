package com.utp.clinica.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PagoRequestDTO {
    private Integer idFactura;
    private BigDecimal monto;
    private String metodo;
    private LocalDate fecha;


}