package com.utp.clinica.controller;

import com.utp.clinica.model.Consultorio;
import com.utp.clinica.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de consultorios de la clínica.
 * <p>
 * Expone los endpoints CRUD bajo la ruta base {@code /api/consultorios}.
 * Permite listar todos los consultorios o solo los disponibles, registrar
 * nuevos, actualizar datos y eliminarlos del sistema.
 * </p>
 */
@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    @Autowired
    private ConsultorioService consultorioService;

    /**
     * Retorna la lista de consultorios disponibles (estado = 1).
     *
     * @return {@code 200 OK} con la lista de consultorios disponibles.
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Consultorio>> listarDisponibles() {
        return ResponseEntity.ok(consultorioService.obtenerDisponibles());
    }

    /**
     * Retorna la lista completa de consultorios, sin importar su estado.
     *
     * @return {@code 200 OK} con la lista de todos los consultorios.
     */
    @GetMapping
    public ResponseEntity<List<Consultorio>> listarTodos() {
        return ResponseEntity.ok(consultorioService.listarTodos());
    }

    /**
     * Registra un nuevo consultorio en el sistema.
     *
     * @param consultorio Datos del consultorio a registrar.
     * @return {@code 201 Created} con el consultorio creado.
     */
    @PostMapping
    public ResponseEntity<Consultorio> registrar(@RequestBody Consultorio consultorio) {
        Consultorio nuevo = consultorioService.guardar(consultorio);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de un consultorio existente.
     *
     * @param id    ID del consultorio a actualizar.
     * @param datos Nuevos datos del consultorio.
     * @return {@code 200 OK} con el consultorio actualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> actualizar(@PathVariable Integer id, @RequestBody Consultorio datos) {
        Consultorio actualizado = consultorioService.actualizar(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Elimina un consultorio del sistema por su ID.
     *
     * @param id ID del consultorio a eliminar.
     * @return {@code 204 No Content} si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        consultorioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
