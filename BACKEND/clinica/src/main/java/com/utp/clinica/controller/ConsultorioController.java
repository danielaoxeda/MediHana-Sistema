package com.utp.clinica.controller;

import com.utp.clinica.model.Consultorio;
import com.utp.clinica.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultorios")
public class ConsultorioController {

    @Autowired
    private ConsultorioService consultorioService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<Consultorio>> listarDisponibles() {
        return ResponseEntity.ok(consultorioService.obtenerDisponibles());
    }

    @GetMapping
    public ResponseEntity<List<Consultorio>> listarTodos() {
        return ResponseEntity.ok(consultorioService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<Consultorio> registrar(@RequestBody Consultorio consultorio) {
        Consultorio nuevo = consultorioService.guardar(consultorio);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultorio> actualizar(@PathVariable Integer id, @RequestBody Consultorio datos) {
        Consultorio actualizado = consultorioService.actualizar(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        consultorioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}