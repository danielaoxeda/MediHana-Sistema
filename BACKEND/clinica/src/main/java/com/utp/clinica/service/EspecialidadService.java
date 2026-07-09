package com.utp.clinica.service;

import com.utp.clinica.DAO.IEspecialidadRepository;
import com.utp.clinica.model.Especialidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    public List<Especialidad> obtenerActivas() {
        return especialidadRepository.findAll().stream()
                .filter(e -> e.getEstado() == 1)
                .toList();
    }

    public List<Especialidad> listarTodas() {
        return especialidadRepository.findAll();
    }

    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Especialidad actualizar(Integer id, Especialidad datos) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        return especialidadRepository.save(existente);
    }

    public void eliminar(Integer id) {
        especialidadRepository.deleteById(id);
    }
}