package com.utp.clinica.DAO;


import com.utp.clinica.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByDni(String dni);
    boolean existsByDni(String dni);

    // Solo devuelve pacientes activos (estado = 1)
    List<Paciente> findByEstado(Integer estado);
}