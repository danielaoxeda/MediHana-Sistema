package com.utp.clinica.controller;

import com.utp.clinica.dto.CitaRequestDTO;
import com.utp.clinica.model.Cita;
import com.utp.clinica.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de citas médicas.
 * <p>
 * Expone los endpoints para agendar, reprogramar, atender y cancelar citas
 * bajo la ruta base {@code /api/citas}.
 * </p>
 */
@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    /**
     * Retorna la lista completa de citas registradas en el sistema.
     *
     * @return {@code 200 OK} con la lista de todas las citas.
     */
    @GetMapping
    public ResponseEntity<List<Cita>> listarTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    /**
     * Agenda una nueva cita médica.
     * <p>
     * Verifica que el médico y el consultorio no tengan conflictos de horario
     * antes de registrar la cita.
     * </p>
     *
     * @param request DTO con los datos de la cita a agendar. Se validan con Bean Validation.
     * @return {@code 201 Created} con la cita registrada,
     *         {@code 400 Bad Request} si hay conflicto de horario o datos inválidos,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping("/agendar")
    public ResponseEntity<?> agendarCita(@Valid @RequestBody CitaRequestDTO request) {
        try {
            Cita nuevaCita = citaService.registrarCita(request);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Reprograma una cita existente asignándole una nueva fecha, hora o médico.
     * <p>
     * Valida que el nuevo horario no genere conflictos con otras citas.
     * </p>
     *
     * @param id      ID de la cita a reprogramar.
     * @param request DTO con los nuevos datos de la cita. Se validan con Bean Validation.
     * @return {@code 200 OK} con la cita actualizada,
     *         o {@code 400 Bad Request} si hay conflicto de horario o la cita no existe.
     */
    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<?> reprogramarCita(@PathVariable Integer id, @Valid @RequestBody CitaRequestDTO request) {
        try {
            Cita citaActualizada = citaService.reprogramarCita(id, request);
            return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Marca una cita como atendida, cambiando su estado a "Atendida".
     *
     * @param id ID de la cita a marcar como atendida.
     * @return {@code 200 OK} con mensaje de confirmación,
     *         {@code 400 Bad Request} si la cita no existe o ya fue atendida/cancelada,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PutMapping("/{id}/atender")
    public ResponseEntity<?> marcarComoAtendida(@PathVariable Integer id) {
        try {
            citaService.marcarComoAtendida(id);
            return new ResponseEntity<>("La cita ha sido marcada como atendida.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cancela una cita médica, cambiando su estado a "Cancelada".
     *
     * @param id ID de la cita a cancelar.
     * @return {@code 200 OK} con mensaje de confirmación,
     *         {@code 400 Bad Request} si la cita no existe o no puede cancelarse,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarCita(@PathVariable Integer id) {
        try {
            citaService.cancelarCita(id);
            return new ResponseEntity<>("La cita fue cancelada exitosamente.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
