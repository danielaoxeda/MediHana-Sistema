package com.utp.clinica.service;

import com.utp.clinica.DAO.IAdministradorRepository;
import com.utp.clinica.model.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private IAdministradorRepository administradorRepository;

    //  CREATE
    public Administrador guardarAdministrador(Administrador administrador) {
        if (administrador.getDni() == null || administrador.getDni().length() != 8) {
            throw new IllegalArgumentException("El DNI del administrador debe tener exactamente 8 dígitos.");
        }

        // validación para evitar DNI duplicados
        if (administradorRepository.findByDni(administrador.getDni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un administrador registrado con ese DNI.");
        }

        try {
            return administradorRepository.save(administrador);
        } catch (Exception e) {
            System.err.println("Error al registrar administrador: " + e.getMessage());
            throw new RuntimeException("Error interno al registrar el administrador en la base de datos.");
        }
    }

    //  READ
    public List<Administrador> obtenerTodosActivos() {
        return administradorRepository.findAll().stream()
                .filter(a -> a.getEstado() == 1) // 1 = Activo
                .toList();
    }

    public Optional<Administrador> buscarPorId(Integer id) {
        return administradorRepository.findById(id);
    }

    public Optional<Administrador> buscarPorDni(String dni) {
        return administradorRepository.findByDni(dni);
    }

    //  UPDATE
    public Administrador actualizarAdministrador(Integer id, Administrador datosActualizados) {
        Administrador adminExistente = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El administrador no existe en el sistema."));

        // actualizamos los campos permitidos
        adminExistente.setNombre(datosActualizados.getNombre());
        adminExistente.setTelefono(datosActualizados.getTelefono());
        adminExistente.setRol(datosActualizados.getRol());

        try {
            return administradorRepository.save(adminExistente);
        } catch (Exception e) {
            System.err.println("Error al actualizar administrador: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar la información del administrador.");
        }
    }

    // DELETE
    public void darDeBajaAdministrador(Integer id) {
        Administrador adminExistente = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El administrador no existe."));

        adminExistente.setEstado(0); // 0 = Inactivo

        try {
            administradorRepository.save(adminExistente);
        } catch (Exception e) {
            System.err.println("Error al dar de baja al administrador: " + e.getMessage());
            throw new RuntimeException("No se pudo dar de baja al administrador.");
        }
    }
}