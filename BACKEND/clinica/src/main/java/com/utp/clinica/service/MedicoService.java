package com.utp.clinica.service;

import com.utp.clinica.DAO.IMedicoRepository;
import com.utp.clinica.model.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {

    @Autowired
    private IMedicoRepository medicoRepository;

    //  CREATE
    public Medico guardarMedico(Medico medico) {
        if (medico.getDni() == null || medico.getDni().length() != 8) {
            throw new IllegalArgumentException("El DNI debe ser de 8 dígitos.");
        }
        try {
            return medicoRepository.save(medico);
        } catch (Exception e) {
            System.err.println("Error al registrar médico: " + e.getMessage());
            throw new RuntimeException("No se pudo registrar al médico.");
        }
    }

    // READ
    public List<Medico> obtenerTodosActivos() {
        return medicoRepository.findAll().stream()
                .filter(m -> m.getEstado() == 1)
                .toList();
    }

    public Optional<Medico> buscarPorId(Integer id) {
        return medicoRepository.findById(id);
    }

    // UPDATE
    public Medico actualizarMedico(Integer id, Medico datosActualizados) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));

        medico.setNombre(datosActualizados.getNombre());
        medico.setTelefono(datosActualizados.getTelefono());
        medico.setHorarioDisponible(datosActualizados.getHorarioDisponible());

        try {
            return medicoRepository.save(medico);
        } catch (Exception e) {
            System.err.println("Error al actualizar médico: " + e.getMessage());
            throw new RuntimeException("Error interno al actualizar médico.");
        }
    }

    // DELETE
    public void darDeBajaMedico(Integer id) {
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado."));
        medico.setEstado(0); // Inactivo
        medicoRepository.save(medico);
    }
}