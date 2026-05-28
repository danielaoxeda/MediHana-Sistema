package com.utp.clinica.controller;

import com.utp.clinica.dto.AuthRequestDTO;
import com.utp.clinica.dto.AuthResponseDTO;
import com.utp.clinica.model.Administrador;
import com.utp.clinica.security.JwtUtil;
import com.utp.clinica.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //  Endpoint para registrar administradores con clave encriptada
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarAdmin(@RequestBody Administrador administrador) {
        try {

            administrador.setPassword(passwordEncoder.encode(administrador.getPassword()));
            Administrador nuevoAdmin = administradorService.guardarAdministrador(administrador);
            return new ResponseEntity<>(nuevoAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //  Endpoint de Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        Optional<Administrador> adminOpt = administradorService.buscarPorDni(request.getDni());

        if (adminOpt.isPresent()) {
            Administrador admin = adminOpt.get();

            if (passwordEncoder.matches(request.getPassword(), admin.getPassword())) {

                String token = jwtUtil.generateToken(admin.getDni(), admin.getRol());
                return ResponseEntity.ok(new AuthResponseDTO(token));
            }
        }
        return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
    }
}