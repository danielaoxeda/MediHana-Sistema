package com.utp.clinica.controller;

import com.utp.clinica.model.Administrador;
import com.utp.clinica.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de administradores del sistema.
 * <p>
 * Expone los endpoints CRUD bajo la ruta base {@code /api/administradores}.
 * Solo los administradores activos (estado = 1) son retornados en las consultas.
 * </p>
 */
@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    /**
     * Retorna la lista de todos los administradores activos (estado = 1).
     *
     * @return {@code 200 OK} con la lista de administradores activos.
     */
    @GetMapping
    public ResponseEntity<List<Administrador>> listarActivos() {
        return ResponseEntity.ok(administradorService.obtenerTodosActivos());
    }

    /**
     * Busca un administrador por su ID.
     *
     * @param id ID del administrador a buscar.
     * @return {@code 200 OK} con el administrador encontrado,
     *         o {@code 404 Not Found} si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Administrador> admin = administradorService.buscarPorId(id);
        if (admin.isPresent()) {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Administrador no encontrado.", HttpStatus.NOT_FOUND);
    }

    /**
     * Registra un nuevo administrador en el sistema.
     *
     * @param administrador Datos del administrador a registrar.
     * @return {@code 201 Created} con el administrador creado,
     *         {@code 400 Bad Request} si los datos son inválidos,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PostMapping
    public ResponseEntity<?> registrarAdministrador(@RequestBody Administrador administrador) {
        try {
            Administrador nuevoAdmin = administradorService.guardarAdministrador(administrador);
            return new ResponseEntity<>(nuevoAdmin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza los datos de un administrador existente.
     *
     * @param id             ID del administrador a actualizar.
     * @param administrador  Nuevos datos del administrador.
     * @return {@code 200 OK} con el administrador actualizado,
     *         {@code 400 Bad Request} si los datos son inválidos,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarAdministrador(@PathVariable Integer id, @RequestBody Administrador administrador) {
        try {
            Administrador actualizado = administradorService.actualizarAdministrador(id, administrador);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Da de baja (soft-delete) a un administrador, estableciendo su estado a inactivo.
     *
     * @param id ID del administrador a dar de baja.
     * @return {@code 200 OK} con mensaje de confirmación,
     *         {@code 400 Bad Request} si el ID no corresponde a un administrador existente,
     *         o {@code 500 Internal Server Error} si ocurre un error inesperado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> darDeBaja(@PathVariable Integer id) {
        try {
            administradorService.darDeBajaAdministrador(id);
            return new ResponseEntity<>("Administrador dado de baja exitosamente.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
