package com.utp.clinica.service;

import com.utp.clinica.DAO.IHistorialMedicoRepository;
import com.utp.clinica.DAO.IPacienteRepository;
import com.utp.clinica.dto.HistorialMedicoRequestDTO;
import com.utp.clinica.model.HistorialMedico;
import com.utp.clinica.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialMedicoService {

    @Autowired
    private IHistorialMedicoRepository historialRepository;

    @Autowired
    private IPacienteRepository pacienteRepository;

    // CREATE
    public HistorialMedico registrarHistorial(HistorialMedicoRequestDTO request) {
        if (request.getDiagnostico() == null || request.getDiagnostico().trim().isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico es obligatorio.");
        }

        try {
            Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                    .orElseThrow(() -> new IllegalArgumentException("El paciente no existe."));

            HistorialMedico nuevoHistorial = new HistorialMedico();
            nuevoHistorial.setPaciente(paciente);
            nuevoHistorial.setDiagnostico(request.getDiagnostico());
            nuevoHistorial.setTratamiento(request.getTratamiento());
            nuevoHistorial.setObservaciones(request.getObservaciones());
            nuevoHistorial.setFecha(request.getFecha());

            return historialRepository.save(nuevoHistorial);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Error interno en HistorialMedicoService: " + e.getMessage());
            throw new RuntimeException("Error al guardar el historial médico.");
        }
    }

    //  READ
    public List<HistorialMedico> obtenerPorPaciente(Integer idPaciente) {
        return historialRepository.findByPacienteId(idPaciente);
    }
}