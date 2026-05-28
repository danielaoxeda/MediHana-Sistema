package com.utp.clinica.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FacturaRequestDTO {
    private Integer idCita;
    private BigDecimal monto;
    private LocalDate fechaEmision;


}