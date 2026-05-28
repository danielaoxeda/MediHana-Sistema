package com.utp.clinica.DAO;

import com.utp.clinica.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ICitaRepository extends JpaRepository<Cita, Integer> {

    List<Cita> findByFecha(LocalDate fecha);

    // valida si un medico ya tiene una cita en estado '1' (programada) en una fecha y hora exacta
    boolean existsByMedicoIdAndFechaAndHoraAndEstado(Integer idMedico, LocalDate fecha, LocalTime hora, Integer estado);

    // Valida si un consultorio ya esta reservado en esa fecha y hora
    boolean existsByConsultorioIdAndFechaAndHoraAndEstado(Integer idConsultorio, LocalDate fecha, LocalTime hora, Integer estado);
}