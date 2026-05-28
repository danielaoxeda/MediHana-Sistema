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
}