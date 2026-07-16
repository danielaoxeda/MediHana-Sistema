package com.utp.clinica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la solicitud de autenticación.
 * Contiene las credenciales que el administrador envía para iniciar sesión.
 */
@Getter
@Setter
public class AuthRequestDTO {

    /**
     * DNI del administrador. Debe tener exactamente 8 dígitos numéricos.
     */
    @NotBlank(message = "El DNI es obligatorio.")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos numéricos.")
    private String dni;

    /**
     * Contraseña del administrador. Mínimo 6 caracteres.
     */
    @NotBlank(message = "La contraseña es obligatoria.")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
    private String password;
}
