package com.utp.clinica.config;

import com.utp.clinica.DAO.IAdministradorRepository;
import com.utp.clinica.DAO.ICitaRepository;
import com.utp.clinica.DAO.IConsultorioRepository;
import com.utp.clinica.DAO.IEspecialidadRepository;
import com.utp.clinica.DAO.IFacturaRepository;
import com.utp.clinica.DAO.IHistorialMedicoRepository;
import com.utp.clinica.DAO.IMedicoRepository;
import com.utp.clinica.DAO.IPacienteRepository;
import com.utp.clinica.DAO.IPagoRepository;
import com.utp.clinica.model.Administrador;
import com.utp.clinica.model.Cita;
import com.utp.clinica.model.Consultorio;
import com.utp.clinica.model.Especialidad;
import com.utp.clinica.model.Factura;
import com.utp.clinica.model.HistorialMedico;
import com.utp.clinica.model.Medico;
import com.utp.clinica.model.Paciente;
import com.utp.clinica.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private IEspecialidadRepository especialidadRepository;
    @Autowired
    private IConsultorioRepository consultorioRepository;
    @Autowired
    private IPacienteRepository pacienteRepository;
    @Autowired
    private IMedicoRepository medicoRepository;
    @Autowired
    private ICitaRepository citaRepository;
    @Autowired
    private IHistorialMedicoRepository historialRepository;
    @Autowired
    private IFacturaRepository facturaRepository;
    @Autowired
    private IPagoRepository pagoRepository;
    @Autowired
    private IAdministradorRepository administradorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Seed test data (for development purposes)
        seedEspecialidades();
        seedConsultorios();
        seedPacientes();
        seedMedicos();
        seedAdministradores();
    }

    private void seedEspecialidades() {
        if (!especialidadRepository.existsByNombre("Cardiología")) {
            Especialidad e1 = new Especialidad();
            e1.setNombre("Cardiología");
            e1.setDescripcion("Enfermedades del corazón y sistema circulatorio");
            e1.setEstado(1);
            especialidadRepository.save(e1);
        }
        if (!especialidadRepository.existsByNombre("Pediatría")) {
            Especialidad e2 = new Especialidad();
            e2.setNombre("Pediatría");
            e2.setDescripcion("Atención médica para niños y adolescentes");
            e2.setEstado(1);
            especialidadRepository.save(e2);
        }
        if (!especialidadRepository.existsByNombre("Dermatología")) {
            Especialidad e3 = new Especialidad();
            e3.setNombre("Dermatología");
            e3.setDescripcion("Tratamiento de enfermedades de la piel");
            e3.setEstado(1);
            especialidadRepository.save(e3);
        }
        if (!especialidadRepository.existsByNombre("Neurología")) {
            Especialidad e4 = new Especialidad();
            e4.setNombre("Neurología");
            e4.setDescripcion("Trastornos del sistema nervioso");
            e4.setEstado(1);
            especialidadRepository.save(e4);
        }
    }

    private void seedConsultorios() {
        if (!consultorioRepository.existsById(101)) {
            Consultorio c1 = new Consultorio();
            c1.setId(101);
            c1.setPiso(1);
            c1.setEstado(1);
            consultorioRepository.save(c1);
        }
        if (!consultorioRepository.existsById(202)) {
            Consultorio c2 = new Consultorio();
            c2.setId(202);
            c2.setPiso(2);
            c2.setEstado(1);
            consultorioRepository.save(c2);
        }
        if (!consultorioRepository.existsById(303)) {
            Consultorio c3 = new Consultorio();
            c3.setId(303);
            c3.setPiso(3);
            c3.setEstado(1);
            consultorioRepository.save(c3);
        }
    }

    private void seedPacientes() {
        if (!pacienteRepository.existsByDni("87654321")) {
            Paciente p1 = new Paciente();
            p1.setNombre("Juan Pérez");
            p1.setDni("87654321");
            p1.setTelefono("999888777");
            p1.setCorreo("juan.perez@example.com");
            p1.setEstado(1);
            pacienteRepository.save(p1);
        }
        if (!pacienteRepository.existsByDni("11223344")) {
            Paciente p2 = new Paciente();
            p2.setNombre("María García");
            p2.setDni("11223344");
            p2.setTelefono("987654321");
            p2.setCorreo("maria.garcia@example.com");
            p2.setEstado(1);
            pacienteRepository.save(p2);
        }
        if (!pacienteRepository.existsByDni("22334455")) {
            Paciente p3 = new Paciente();
            p3.setNombre("Carlos Ramírez");
            p3.setDni("22334455");
            p3.setTelefono("912345678");
            p3.setCorreo("carlos.ramirez@example.com");
            p3.setEstado(1);
            pacienteRepository.save(p3);
        }
    }

    private void seedMedicos() {
        if (!medicoRepository.existsByDni("11223344")) {
            Medico m1 = new Medico();
            m1.setNombre("Dra. López");
            m1.setDni("11223344");
            m1.setHorarioDisponible("Lun-Vie 8am-5pm");
            m1.setTelefono("988776655");
            m1.setEstado(1);
            medicoRepository.save(m1);
        }
        if (!medicoRepository.existsByDni("55443322")) {
            Medico m2 = new Medico();
            m2.setNombre("Dr. Gómez");
            m2.setDni("55443322");
            m2.setHorarioDisponible("Lun-Sáb 9am-6pm");
            m2.setTelefono("977665544");
            m2.setEstado(1);
            medicoRepository.save(m2);
        }
        if (!medicoRepository.existsByDni("66778899")) {
            Medico m3 = new Medico();
            m3.setNombre("Dra. Fernández");
            m3.setDni("66778899");
            m3.setHorarioDisponible("Mar-Juev 10am-4pm");
            m3.setTelefono("966554433");
            m3.setEstado(1);
            medicoRepository.save(m3);
        }
    }

    private void seedAdministradores() {
        if (!administradorRepository.existsByDni("12345678")) {
            Administrador admin1 = new Administrador();
            admin1.setNombre("Admin Default");
            admin1.setDni("12345678");
            admin1.setPassword(passwordEncoder.encode("admin123"));
            admin1.setRol("ADMIN");
            admin1.setEstado(1);
            administradorRepository.save(admin1);
        }
        if (!administradorRepository.existsByDni("87654321")) {
            Administrador admin2 = new Administrador();
            admin2.setNombre("Admin Test");
            admin2.setDni("87654321");
            admin2.setPassword(passwordEncoder.encode("test123"));
            admin2.setRol("ADMIN");
            admin2.setEstado(1);
            administradorRepository.save(admin2);
        }
    }
}
