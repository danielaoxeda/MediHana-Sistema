CREATE DATABASE clinica_db;
USE clinica_db;


drop database clinica_db;

-- 1 TABLAS INDEPENDIENTES

CREATE TABLE pacientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    estado TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1: Activo, 0: Inactivo',
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    horario_disponible VARCHAR(100),
    estado TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1: Activo, 0: Inactivo',
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE administradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    rol VARCHAR(50),
    estado TINYINT(1) NOT NULL DEFAULT 1,
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE especialidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE consultorios (
    id INT PRIMARY KEY, -- Mantengo que el ID sea el número del consultorio (ej. 101, 204)
    piso INT NOT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1: Disponible, 0: Ocupado/Mantenimiento',
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2 TABLAS DEPENDIENTES (con llaves foráneas)

CREATE TABLE historiales_medicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    diagnostico TEXT,
    tratamiento TEXT,
    observaciones TEXT,
    fecha DATE,
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id)
);

CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_medico INT NOT NULL,
    id_consultorio INT NOT NULL,
    id_especialidad INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1: Programada, 2: Atendida, 3: Cancelada, 4: Reprogramada',
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cita_paciente FOREIGN KEY (id_paciente) REFERENCES pacientes(id),
    CONSTRAINT fk_cita_medico FOREIGN KEY (id_medico) REFERENCES medicos(id),
    CONSTRAINT fk_cita_consultorio FOREIGN KEY (id_consultorio) REFERENCES consultorios(id),
    CONSTRAINT fk_cita_especialidad FOREIGN KEY (id_especialidad) REFERENCES especialidades(id)
);

CREATE TABLE facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_emision DATE NOT NULL,
    estado_pago TINYINT(1) NOT NULL DEFAULT 0 COMMENT '0: Pendiente, 1: Pagada',
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_factura_cita FOREIGN KEY (id_cita) REFERENCES citas(id)
);

CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    fecha DATE NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    metodo VARCHAR(50) NOT NULL,
    creado_el DATETIME DEFAULT CURRENT_TIMESTAMP,
    actualizado_el TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_pago_factura FOREIGN KEY (id_factura) REFERENCES facturas(id)
);


INSERT INTO especialidades (nombre, descripcion) VALUES ('Odontología General', 'Atención dental básica y limpiezas');
INSERT INTO consultorios (id, piso, estado) VALUES (101, 1, 1);
