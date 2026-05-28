package com.utp.clinica.DAO;

import com.utp.clinica.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultorioRepository extends JpaRepository<Consultorio, Integer> {
    List<Consultorio> findByEstado(Integer estado); // pa buscar los consultorios disponibles (estado = 1)
}