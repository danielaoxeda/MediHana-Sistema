package com.utp.clinica.controller;

import com.utp.clinica.dto.HistorialMedicoRequestDTO;
import com.utp.clinica.model.HistorialMedico;
import com.utp.clinica.service.HistorialMedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de historiales médicos.
 * <p>
 * Expone los endpoints para consultar y registrar historiales clínicos
 * de los pacientes bajo la ruta base {@code /api/historiales}.
 * Cada historial queda asociado a un paciente y representa el registro
 * de una consulta médica con su diagnóstico y tratamiento.
 * </p>
 */
@RestController
@RequestMapping("/api/historiales")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialService;

    /**
     * Retorna todos los historiales médicos de un paciente específico.
     *
     * @param idPaciente ID del paciente cuyos historiales se desean consultar.
     * @return {@code 200 OK} con la lista de historiales del paciente.
     */
    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<HistorialMedico>> listarPorPaciente(@PathVariable Integer idPaciente) {
        return ResponseEntity.ok(historialService.obtenerPorPaciente(idPaciente));
    }

    /**
     * Retorna la lista completa de historiales médicos registrados en el sistema.
     *
     * @return {@code 200 OK} con todos los historiales médicos.
     */
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarTodos() {
        return ResponseEntity.ok(historialService.obtenerTodos());
    }

    /**
     * Registra un nuevo historial médico para un paciente.
     * <p>
     * Valida que el paciente exista antes de guardar el registro clínico.
     * </p>
     *
     * @param request DTO con los datos del historial a registrar. Se validan con Bean Validation.
     * @return {@code 201 Created} con el historial creado,
     *         {@code 400 Bad Request} si el paciente no existe o los datos son inválidos,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping
    public ResponseEntity<?> registrarHistorial(@Valid @RequestBody HistorialMedicoRequestDTO request) {
        try {
            HistorialMedico nuevo = historialService.registrarHistorial(request);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
