# 🏥💻 MediHana-Sistema

> Sistema de gestión de citas y consultorios para clínica MediHana. Automatiza registros, asignación y reportes, resolviendo problemas de gestión manual.

![Java](https://img.shields.io/badge/Java-17-00739F?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-00758F?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apache-maven)
![Git](https://img.shields.io/badge/Git-F05032?logo=git)

---

## 📌 Sobre el proyecto

**MediHana-Sistema** es una solución de software diseñada para una clínica privada de atención ambulatoria que ofrece servicios de medicina general, pediatría, cardiología, oncología y otras especialidades.

Actualmente, la gestión de citas se realiza de forma manual o con hojas de cálculo dispersas, lo que genera errores, duplicidad de reservas y falta de control en tiempo real. Este sistema busca **automatizar el proceso completo** de registro de pacientes, asignación de citas, control de disponibilidad de médicos y consultorios, y generación de reportes básicos.

### 🎯 Problema a resolver
- Registro manual propenso a errores y duplicidad.
- Desconocimiento de la disponibilidad real de médicos y consultorios.
- Pérdida o desorden en el historial clínico de citas.
- Falta de control administrativo sobre cancelaciones y ausencias.
- Ausencia de reportes claros sobre el estado de las citas.

### ✨ Solución propuesta
El sistema permitirá:
- Registrar y gestionar pacientes, médicos y consultorios.
- Programar, cancelar y reprogramar citas con validación de disponibilidad.
- Consultar historiales de citas por paciente.
- Generar reportes de citas (programadas, atendidas, canceladas) por especialidad.
- Persistir toda la información en una base de datos segura en la nube.

---

## 🏗️ Arquitectura y Paradigma

### Patrón de Diseño: POO (Programación Orientada a Objetos)
El sistema se fundamenta en **POO** para modelar el dominio de la clínica mediante entidades claras y relaciones de herencia. La jerarquía principal se basa en la clase abstracta `Persona`, que se especializa en `Paciente`, `Médico` y `Administrador`.

Esto permite:
- **Herencia:** Reducción de código repetitivo (DNI, nombre, teléfono) mediante la clase base `Persona`.
- **Encapsulación:** Protección de datos sensibles como `historial_medico`, `diagnósticos` y datos de pago (`Factura`, `Pago`).
- **Abstracción:** Separación de la lógica de negocio (`SistemaClinica`) de la implementación de datos.
- **Asociaciones:** Relaciones definidas entre `Cita`, `Paciente`, `Médico`, `Consultorio` y `Especialidad`.

**Jerarquía de Clases Principal:**
- `Persona` (Base)
  - `Paciente` (+ `historial_citas`, `historial_medico`)
  - `Medico` (+ `horario_disponible`, `especialidades`)
  - `Administrador` (+ `rol`)
- `Especialidad`, `Consultorio`, `Cita`, `HistorialMedico`, `Factura`, `Pago`

### Arquitectura: MVC (Modelo-Vista-Controlador)
Se adopta el patrón **MVC** para separar las responsabilidades, asegurando que la lógica de negocio (validación de citas, cálculo de pagos) no se mezcle con la interfaz o la base de datos.

- **Modelo:** Representa las entidades (`Paciente`, `Cita`, `Factura`) y la lógica de acceso a datos (`DAO`).
- **Vista:** Interfaz de usuario (Consola o GUI) para la gestión de citas y reportes.
- **Controlador:** Orquesta el flujo, gestionando la creación de `Citas`, la asignación de `Consultorios` y el procesamiento de `Pagos`.

### Estructura del Proyecto (Tree)

```txt
src/
├── main/
│   ├── java/
│   │   └── com/medihana/
│   │       ├── model/                 # Entidades del dominio
│   │       │   ├── Persona.java       # Clase base (id, dni, nombre, telefono, correo)
│   │       │   ├── Paciente.java      # Hereda de Persona + historial_citas, historial_medico
│   │       │   ├── Medico.java        # Hereda de Persona + horario, especialidades
│   │       │   ├── Administrador.java # Hereda de Persona + rol
│   │       │   ├── Especialidad.java  # id, nombre, descripcion
│   │       │   ├── Consultorio.java   # numero, piso, estado, especialidad_asignada
│   │       │   ├── Cita.java          # Relación: Paciente, Medico, Consultorio, Especialidad
│   │       │   ├── HistorialMedico.java # Diagnostico, tratamiento, observaciones
│   │       │   ├── Factura.java       # Relación: Cita, monto, estado_pago
│   │       │   └── Pago.java          # Relación: Factura, metodo, fecha
│   │       │
│   │       ├── dao/                   # Acceso a Datos (JDBC)
│   │       │   ├── PersonaDAO.java
│   │       │   ├── CitaDAO.java
│   │       │   └── PagoDAO.java
│   │       │
│   │       ├── service/               # Lógica de Negocio
│   │       │   ├── CitasService.java  # Validar disponibilidad, programar, cancelar
│   │       │   ├── ReportesService.java
│   │       │   └── FacturacionService.java
│   │       │
│   │       ├── controller/            # Controladores (Interfaz)
│   │       │   ├── MenuController.java
│   │       │   └── CitasController.java
│   │       │
│   │       ├── util/
│   │       │   ├── DatabaseConnection.java
│   │       │   └── Validator.java
│   │       │
│   │       └── Main.java              # Punto de entrada (SistemaClinica)
│   │
│   └── resources/
│       └── config.properties          # Credenciales de BD
│
└── test/
    └── java/                          # Pruebas unitarias de entidades y servicios

## 🛠️ Tecnologías utilizadas

| Tecnología | Propósito |
|------------|-----------|
| **Java 17+** | Lenguaje principal (Tipado fuerte, POO, multiplataforma) |
| **MySQL 8.0** | Sistema de gestión de base de datos relacional |
| **JDBC** | Conectividad segura y directa con la base de datos |
| **Maven** | Gestión de dependencias y construcción del proyecto |
| **Git** | Control de versiones y trabajo colaborativo |

--- 

## 🔒 Seguridad y vulnerabilidades
El sistema prioriza la seguridad de los datos de los pacientes:

- Inyección SQL: Uso obligatorio de PreparedStatement (JDBC en Java) para todas las consultas.
- XSS: Sanitización de entradas de usuario antes de mostrarlas en la interfaz.
- Contraseñas: Hashing seguro utilizando BCrypt o Argon2 (si hubiera usuarios con login).
- Control de Acceso (RBAC): Validación de roles (Administrador vs. Recepcionista) antes de ejecutar acciones críticas.

---

## 🛡️ Manejo de Errores y Logs
- Try-Catch: Bloques específicos para capturar SQLException, IOException, etc.
- Logs: Los errores no se muestran al usuario final en producción. Se registran en logs del servidor (logs/app.log) con detalles técnicos.
- Mensajes al Usuario: Mensajes genéricos y amigables (ej: "Ocurrió un error al procesar la solicitud").

---

## 🔄 Flujo de Trabajo (Git)
Para el control de versiones, se sigue el siguiente flujo:

- Ramas:
  - main: Código estable y listo para producción.
  - develop: Rama de integración.
  - feature/nombre: Para nuevas funcionalidades (ej: feature/gestion-citas).
  - bugfix/nombre: Para correcciones urgentes.
- Commit Messages: Semánticos y descriptivos (ej: feat: agregar validación de disponibilidad de consultorio).
- Pull Requests: Revisión obligatoria por pares antes de fusionar a main.

---

## 🚀 Instalación y ejecución
### 📋 Requisitos previos
- JDK 17 o superior
- MySQL Server 8.0+
- Maven 3.6+
- Git
### 📦Instalación
1. Clonar el repositorio:
 ```bash
git clone https://github.com/daniela_oxeda/MediHana-Sistema.git
cd MediHana-Sistema
```
2. Configurar la base de datos:
- Crear la base de datos medihana_db.
- Importar el script inicial schema.sql.
- Editar el archivo src/main/resources/config.properties con sus credenciales de MySQL.
3. Compilar el proyecto:
  ```bash
  mvn clean compile
  ```
### ▶️ Ejecutar el proyecto
```bash
mvn exec:java -Dexec.mainClass="com.medihana.Main"
```
---

## 👥 Equipo y colaboración
Proyecto académico desarrollado para la gestión eficiente de la clínica MediHana en el curso Lenguajes de Programación.

- Desarrollo: Equipo de estudiantes de Ingeniería de Software y Sistemas.
- Liderazgo: Coordinación de proyectos tecnológicos.

--- 

## 📌 Estado del proyecto
### 🚧 En desarrollo activo

Actualmente se está implementando:

- Estructura base del proyecto Java y conexión a BD.
- Modelado de entidades (Paciente, Médico, Cita).
- Lógica de negocio para asignación de citas.
- Generación de reportes básicos.

---

## 📄 Licencia
Proyecto desarrollado con fines académicos y de gestión interna.
