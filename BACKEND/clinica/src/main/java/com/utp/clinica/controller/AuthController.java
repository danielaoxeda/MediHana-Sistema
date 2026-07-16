package com.utp.clinica.controller;

import com.utp.clinica.dto.AuthRequestDTO;
import com.utp.clinica.dto.AuthResponseDTO;
import com.utp.clinica.model.Administrador;
import com.utp.clinica.security.JwtUtil;
import com.utp.clinica.service.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para la autenticación y registro de administradores.
 * <p>
 * Expone los endpoints de seguridad bajo la ruta base {@code /api/auth}.
 * Gestiona el registro con contraseña encriptada y el login con generación de JWT.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registra un nuevo administrador en el sistema con su contraseña encriptada (BCrypt).
     * <p>
     * La contraseña recibida en texto plano es codificada antes de persistirse en la base de datos.
     * </p>
     *
     * @param administrador Datos del administrador a registrar, incluyendo la contraseña en texto plano.
     * @return {@code 201 Created} con el administrador registrado,
     *         o {@code 400 Bad Request} si los datos son inválidos o el DNI ya existe.
     */
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

    /**
     * Autentica a un administrador y devuelve un token JWT si las credenciales son válidas.
     * <p>
     * Verifica el DNI y la contraseña contra los registros almacenados. Si son correctos,
     * genera y retorna un token JWT firmado con el rol del administrador.
     * </p>
     *
     * @param request DTO con el DNI y contraseña del administrador. Se validan con Bean Validation.
     * @return {@code 200 OK} con el token JWT en el cuerpo,
     *         o {@code 401 Unauthorized} si las credenciales son incorrectas.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO request) {
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
