package com.utp.clinica.controller;

import com.utp.clinica.model.Consultorio;
import com.utp.clinica.service.ConsultorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}