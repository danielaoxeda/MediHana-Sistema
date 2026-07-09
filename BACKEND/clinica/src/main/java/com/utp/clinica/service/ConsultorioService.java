package com.utp.clinica.service;

import com.utp.clinica.DAO.IConsultorioRepository;
import com.utp.clinica.model.Consultorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultorioService {

    @Autowired
    private IConsultorioRepository consultorioRepository;

    public List<Consultorio> obtenerDisponibles() {
        return consultorioRepository.findByEstado(1);
    }

    public List<Consultorio> listarTodos() {
        return consultorioRepository.findAll();
    }

    public Consultorio guardar(Consultorio consultorio) {
        // Generar próximo ID si no se proporcionó
        if (consultorio.getId() == null) {
            Integer maxId = consultorioRepository.findAll().stream()
                    .map(Consultorio::getId)
                    .max(Integer::compareTo)
                    .orElse(0);
            consultorio.setId(maxId + 1);
        }
        return consultorioRepository.save(consultorio);
    }

    public Consultorio actualizar(Integer id, Consultorio datos) {
        Consultorio existente = consultorioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consultorio no encontrado con id: " + id));
        existente.setPiso(datos.getPiso());
        existente.setEstado(datos.getEstado());
        return consultorioRepository.save(existente);
    }

    public void eliminar(Integer id) {
        consultorioRepository.deleteById(id);
    }
}