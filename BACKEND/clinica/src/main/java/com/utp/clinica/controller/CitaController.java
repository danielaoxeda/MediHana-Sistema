package com.utp.clinica.controller;

import com.utp.clinica.dto.CitaRequestDTO;
import com.utp.clinica.model.Cita;
import com.utp.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> listarTodas() {
        return ResponseEntity.ok(citaService.obtenerTodas());
    }

    @PostMapping("/agendar")
    public ResponseEntity<?> agendarCita(@RequestBody CitaRequestDTO request) {
        try {
            // El servicio lanza excepciones si el médico o el consultorio están ocupados
            Cita nuevaCita = citaService.registrarCita(request);
            return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Captura los errores de validación de negocio y devuelve un Error 400
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<?> reprogramarCita(@PathVariable Integer id, @RequestBody CitaRequestDTO request) {
        try {
            Cita citaActualizada = citaService.reprogramarCita(id, request);
            return new ResponseEntity<>(citaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //  UPDATE (Marcar como atendida)
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

    //  DELETE
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