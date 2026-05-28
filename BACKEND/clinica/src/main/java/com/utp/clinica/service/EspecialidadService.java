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

    // retorna solo las especialidades activas para mostrarlas en el frontend
    public List<Especialidad> obtenerActivas() {
        return especialidadRepository.findAll().stream()
                .filter(e -> e.getEstado() == 1)
                .toList();
    }
}