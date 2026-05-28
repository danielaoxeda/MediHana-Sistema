package com.utp.clinica.service;

import com.utp.clinica.DAO.*;
import com.utp.clinica.dto.CitaRequestDTO;
import com.utp.clinica.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaService {

    @Autowired private ICitaRepository citaRepository;
    @Autowired private IPacienteRepository pacienteRepository;
    @Autowired private IMedicoRepository medicoRepository;
    @Autowired private IConsultorioRepository consultorioRepository;
    @Autowired private IEspecialidadRepository especialidadRepository;

    @Transactional
    public Cita registrarCita(CitaRequestDTO request) {

        //validar disponibilidad del medico y consultorio (estado 1 = programada) xd miren la bd
        boolean medicoOcupado = citaRepository.existsByMedicoIdAndFechaAndHoraAndEstado(
                request.getIdMedico(), request.getFecha(), request.getHora(), 1);

        if (medicoOcupado) {
            throw new IllegalArgumentException("El médico seleccionado no está disponible en esa fecha y hora.");
        }

        boolean consultorioOcupado = citaRepository.existsByConsultorioIdAndFechaAndHoraAndEstado(
                request.getIdConsultorio(), request.getFecha(), request.getHora(), 1);

        if (consultorioOcupado) {
            throw new IllegalArgumentException("El consultorio seleccionado se encuentra ocupado en ese horario.");
        }

        // procesamiento transaccional de la reserva
        try {
            Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
                    .orElseThrow(() -> new IllegalArgumentException("El paciente no existe en el sistema."));
            Medico medico = medicoRepository.findById(request.getIdMedico())
                    .orElseThrow(() -> new IllegalArgumentException("El médico no existe."));
            Consultorio consultorio = consultorioRepository.findById(request.getIdConsultorio())
                    .orElseThrow(() -> new IllegalArgumentException("El consultorio no existe."));
            Especialidad especialidad = especialidadRepository.findById(request.getIdEspecialidad())
                    .orElseThrow(() -> new IllegalArgumentException("Especialidad inválida."));

            Cita nuevaCita = new Cita();
            nuevaCita.setPaciente(paciente);
            nuevaCita.setMedico(medico);
            nuevaCita.setConsultorio(consultorio);
            nuevaCita.setEspecialidad(especialidad);
            nuevaCita.setFecha(request.getFecha());
            nuevaCita.setHora(request.getHora());
            nuevaCita.setEstado(1); // 1 = Programada

            return citaRepository.save(nuevaCita);

        } catch (IllegalArgumentException e) {
            throw e; // rlanza los errores de negocio para que lleguen al usuario
        } catch (Exception e) {
            System.err.println("Error crítico en CitaService::registrarCita -> " + e.getMessage());
            throw new RuntimeException("Ocurrió un error interno al intentar registrar la cita médica.");
        }
    }

    //  READ
    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    public Cita buscarPorId(Integer id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada."));
    }

    //  UPDATE
    public Cita reprogramarCita(Integer id, CitaRequestDTO nuevaFechaHora) {
        Cita citaExistente = buscarPorId(id);

        if (citaExistente.getEstado() != 1) {
            throw new IllegalArgumentException("Solo se pueden reprogramar citas que estén en estado 'Programada'.");
        }

        // validar nueva disponibilidad
        if (citaRepository.existsByMedicoIdAndFechaAndHoraAndEstado(citaExistente.getMedico().getId(), nuevaFechaHora.getFecha(), nuevaFechaHora.getHora(), 1)) {
            throw new IllegalArgumentException("El médico no tiene disponibilidad en el nuevo horario.");
        }

        citaExistente.setFecha(nuevaFechaHora.getFecha());
        citaExistente.setHora(nuevaFechaHora.getHora());
        citaExistente.setEstado(4); // 4 = Reprogramada [cite: 129, 62] cheken la bd

        try {
            return citaRepository.save(citaExistente);
        } catch (Exception e) {
            System.err.println("Error al reprogramar cita: " + e.getMessage());
            throw new RuntimeException("No se pudo reprogramar la cita.");
        }
    }

    // UPDATE pa maracar como antendida
    public void marcarComoAtendida(Integer id) {
        Cita cita = buscarPorId(id);
        cita.setEstado(2); // 2 = Atendida [cite: 129, 62]
        citaRepository.save(cita);
    }


    // --- DELETE (Cancelar - Lógico) ---
    public void cancelarCita(Integer id) {
        Cita cita = buscarPorId(id);

        if (cita.getEstado() == 2) {
            throw new IllegalArgumentException("No se puede cancelar una cita que ya fue atendida.");
        }

        cita.setEstado(3); // 3 = Cancelada [cite: 129, 62]

        try {
            citaRepository.save(cita);
        } catch (Exception e) {
            System.err.println("Error al cancelar cita: " + e.getMessage());
            throw new RuntimeException("No se pudo cancelar la cita.");
        }
    }
}