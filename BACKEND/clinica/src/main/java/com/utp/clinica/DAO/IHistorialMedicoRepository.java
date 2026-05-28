package com.utp.clinica.DAO;

import com.utp.clinica.model.HistorialMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistorialMedicoRepository extends JpaRepository<HistorialMedico, Integer> {
    List<HistorialMedico> findByPacienteId(Integer idPaciente); // para listar el historial de un paciente específico falta control y services
}