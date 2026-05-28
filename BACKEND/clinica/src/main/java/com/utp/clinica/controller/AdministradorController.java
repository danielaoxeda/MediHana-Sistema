package com.utp.clinica.controller;

import com.utp.clinica.model.Administrador;
import com.utp.clinica.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    //  READ
    @GetMapping
    public ResponseEntity<List<Administrador>> listarActivos() {
        return ResponseEntity.ok(administradorService.obtenerTodosActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Administrador> admin = administradorService.buscarPorId(id);
        if (admin.isPresent()) {
            return new ResponseEntity<>(admin.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Administrador no encontrado.", HttpStatus.NOT_FOUND);
    }

    //  CREATE
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

    //  UPDATE
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

    //  DELETE
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