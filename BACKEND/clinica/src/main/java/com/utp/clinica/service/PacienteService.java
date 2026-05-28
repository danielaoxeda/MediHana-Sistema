package com.utp.clinica.service;

import com.utp.clinica.DAO.IPacienteRepository;
import com.utp.clinica.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorDni(String dni) {
        try {
            return pacienteRepository.findByDni(dni);
        } catch (Exception e) {
            System.err.println("Error en PacienteService::buscarPorDni -> " + e.getMessage());
            throw new RuntimeException("Inconveniente técnico al consultar la base de datos.");
        }
    }

    public Optional<Paciente> buscarPorId(Integer id) {
        return pacienteRepository.findById(id);
    }

    public Paciente guardarPaciente(Paciente paciente) {
        // vq alidaciones de negocio antes de tocar la base de datos
        if (paciente.getDni() == null || paciente.getDni().length() < 8) {
            throw new IllegalArgumentException("El número de DNI debe ser válido.");
        }

        try {
            return pacienteRepository.save(paciente);
        } catch (Exception e) {
            System.err.println("Error en PacienteService::guardarPaciente -> " + e.getMessage());
            throw new RuntimeException("Error interno al registrar el paciente.");
        }
    }

    //UPDATE
    public Paciente actualizarPaciente(Integer id, Paciente datosActualizados) {
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El paciente no existe."));

        pacienteExistente.setNombre(datosActualizados.getNombre());
        pacienteExistente.setTelefono(datosActualizados.getTelefono());
        pacienteExistente.setCorreo(datosActualizados.getCorreo());

        try {
            return pacienteRepository.save(pacienteExistente);
        } catch (Exception e) {
            System.err.println("Error al actualizar paciente: " + e.getMessage());
            throw new RuntimeException("No se pudo actualizar la información del paciente.");
        }
    }

    // DELETE
    public void EliminarPaciente(Integer id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El paciente no existe."));

        paciente.setEstado(0); // 0 = Inactivo

        try {
            pacienteRepository.save(paciente);
        } catch (Exception e) {
            System.err.println("Error al dar de baja paciente: " + e.getMessage());
            throw new RuntimeException("No se pudo dar de baja al paciente.");
        }
    }

}