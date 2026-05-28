package com.utp.clinica.controller;

import com.utp.clinica.model.Medico;
import com.utp.clinica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    //  READ
    @GetMapping
    public ResponseEntity<List<Medico>> listarActivos() {
        return ResponseEntity.ok(medicoService.obtenerTodosActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Medico> medico = medicoService.buscarPorId(id);
        if (medico.isPresent()) {
            return new ResponseEntity<>(medico.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Médico no encontrado.", HttpStatus.NOT_FOUND);
    }

    //  CREATE
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

    //  UPDATE
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

    //  DELETE
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