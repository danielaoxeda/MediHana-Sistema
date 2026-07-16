package com.utp.clinica.controller;

import com.utp.clinica.model.Paciente;
import com.utp.clinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de pacientes.
 * <p>
 * Expone los endpoints CRUD bajo la ruta base {@code /api/pacientes}.
 * Solo los pacientes activos (estado = 1) son retornados en el listado.
 * La eliminación es un soft-delete que marca al paciente como inactivo,
 * preservando la integridad referencial con citas e historiales médicos.
 * </p>
 */
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    /**
     * Retorna la lista de todos los pacientes activos (estado = 1).
     *
     * @return {@code 200 OK} con la lista de pacientes activos.
     */
    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(pacienteService.obtenerTodos());
    }

    /**
     * Busca un paciente por su número de DNI.
     *
     * @param dni DNI del paciente a buscar (8 dígitos).
     * @return {@code 200 OK} con los datos del paciente encontrado,
     *         {@code 404 Not Found} si no existe ningún paciente con ese DNI,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @GetMapping("/buscar/{dni}")
    public ResponseEntity<?> buscarPorDni(@PathVariable String dni) {
        try {
            Optional<Paciente> paciente = pacienteService.buscarPorDni(dni);
            if (paciente.isPresent()) {
                return new ResponseEntity<>(paciente.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Paciente no registrado en el sistema.", HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Registra un nuevo paciente en el sistema.
     * <p>
     * Valida que el DNI tenga al menos 8 dígitos y que no esté ya registrado.
     * </p>
     *
     * @param paciente Datos del paciente a registrar.
     * @return {@code 201 Created} con el paciente registrado,
     *         {@code 400 Bad Request} si el DNI es inválido o ya está en uso,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarPaciente(@RequestBody Paciente paciente) {
        try {
            Paciente nuevo = pacienteService.guardarPaciente(paciente);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza los datos de contacto de un paciente existente (teléfono, correo y nombre).
     * <p>
     * No permite cambiar el DNI del paciente una vez registrado.
     * </p>
     *
     * @param id       ID del paciente a actualizar.
     * @param paciente Nuevos datos del paciente.
     * @return {@code 200 OK} con el paciente actualizado,
     *         {@code 400 Bad Request} si el paciente no existe,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPaciente(@PathVariable Integer id, @RequestBody Paciente paciente) {
        try {
            Paciente actualizado = pacienteService.actualizarPaciente(id, paciente);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Da de baja (soft-delete) a un paciente, estableciendo su estado a inactivo (estado = 0).
     * <p>
     * El paciente no es eliminado físicamente de la base de datos.
     * Tras la baja, el paciente deja de aparecer en el listado activo.
     * </p>
     *
     * @param id ID del paciente a dar de baja.
     * @return {@code 200 OK} con mensaje de confirmación,
     *         {@code 400 Bad Request} si el paciente no existe,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id) {
        try {
            pacienteService.EliminarPaciente(id);
            return new ResponseEntity<>("Paciente dado de baja exitosamente.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
