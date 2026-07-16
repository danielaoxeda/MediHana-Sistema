package com.utp.clinica.controller;

import com.utp.clinica.model.Medico;
import com.utp.clinica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión del personal médico.
 * <p>
 * Expone los endpoints CRUD bajo la ruta base {@code /api/medicos}.
 * Solo los médicos activos (estado = 1) son retornados en el listado principal.
 * La eliminación es un soft-delete que marca al médico como inactivo.
 * </p>
 */
@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    /**
     * Retorna la lista de todos los médicos activos (estado = 1).
     *
     * @return {@code 200 OK} con la lista de médicos activos.
     */
    @GetMapping
    public ResponseEntity<List<Medico>> listarActivos() {
        return ResponseEntity.ok(medicoService.obtenerTodosActivos());
    }

    /**
     * Busca un médico por su ID.
     *
     * @param id ID del médico a buscar.
     * @return {@code 200 OK} con los datos del médico encontrado,
     *         o {@code 404 Not Found} si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Medico> medico = medicoService.buscarPorId(id);
        if (medico.isPresent()) {
            return new ResponseEntity<>(medico.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Médico no encontrado.", HttpStatus.NOT_FOUND);
    }

    /**
     * Registra un nuevo médico en el sistema.
     *
     * @param medico Datos del médico a registrar.
     * @return {@code 201 Created} con el médico registrado,
     *         {@code 400 Bad Request} si los datos son inválidos (ej. DNI duplicado),
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping
    public ResponseEntity<?> registrarMedico(@RequestBody Medico medico) {
        try {
            Medico nuevoMedico = medicoService.guardarMedico(medico);
            return new ResponseEntity<>(nuevoMedico, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza los datos de un médico existente.
     *
     * @param id     ID del médico a actualizar.
     * @param medico Nuevos datos del médico.
     * @return {@code 200 OK} con el médico actualizado,
     *         {@code 400 Bad Request} si los datos son inválidos,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedico(@PathVariable Integer id, @RequestBody Medico medico) {
        try {
            Medico actualizado = medicoService.actualizarMedico(id, medico);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Da de baja (soft-delete) a un médico, estableciendo su estado a inactivo.
     * <p>
     * El médico no es eliminado físicamente de la base de datos,
     * solo se marca con estado = 0 para preservar la integridad referencial
     * con citas e historiales existentes.
     * </p>
     *
     * @param id ID del médico a dar de baja.
     * @return {@code 200 OK} con mensaje de confirmación,
     *         o {@code 400 Bad Request} si el ID no corresponde a un médico existente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id) {
        try {
            medicoService.darDeBajaMedico(id);
            return new ResponseEntity<>("Médico dado de baja exitosamente.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
