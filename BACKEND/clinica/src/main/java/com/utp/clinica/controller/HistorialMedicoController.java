package com.utp.clinica.controller;

import com.utp.clinica.dto.HistorialMedicoRequestDTO;
import com.utp.clinica.model.HistorialMedico;
import com.utp.clinica.service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialService;

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<List<HistorialMedico>> listarPorPaciente(@PathVariable Integer idPaciente) {
        return ResponseEntity.ok(historialService.obtenerPorPaciente(idPaciente));
    }

    @PostMapping
    public ResponseEntity<?> registrarHistorial(@RequestBody HistorialMedicoRequestDTO request) {
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