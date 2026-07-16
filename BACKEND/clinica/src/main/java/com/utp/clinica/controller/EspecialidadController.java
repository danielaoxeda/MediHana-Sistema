package com.utp.clinica.controller;

import com.utp.clinica.model.Especialidad;
import com.utp.clinica.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de especialidades médicas.
 * <p>
 * Expone los endpoints CRUD bajo la ruta base {@code /api/especialidades}.
 * Solo retorna especialidades activas (estado = 1) en el listado principal.
 * </p>
 */
@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    /**
     * Retorna la lista de especialidades activas (estado = 1).
     *
     * @return {@code 200 OK} con la lista de especialidades activas.
     */
    @GetMapping
    public ResponseEntity<List<Especialidad>> listarActivas() {
        return ResponseEntity.ok(especialidadService.obtenerActivas());
    }

    /**
     * Registra una nueva especialidad médica en el sistema.
     *
     * @param especialidad Datos de la especialidad a registrar.
     * @return {@code 201 Created} con la especialidad creada.
     */
    @PostMapping
    public ResponseEntity<Especialidad> registrar(@RequestBody Especialidad especialidad) {
        Especialidad nueva = especialidadService.guardar(especialidad);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    /**
     * Actualiza los datos de una especialidad existente.
     *
     * @param id    ID de la especialidad a actualizar.
     * @param datos Nuevos datos de la especialidad.
     * @return {@code 200 OK} con la especialidad actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizar(@PathVariable Integer id, @RequestBody Especialidad datos) {
        Especialidad actualizada = especialidadService.actualizar(id, datos);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una especialidad del sistema por su ID.
     *
     * @param id ID de la especialidad a eliminar.
     * @return {@code 204 No Content} si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        especialidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
