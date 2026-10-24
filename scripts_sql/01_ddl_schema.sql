DROP DATABASE IF EXISTS gestion_proyectos;

CREATE DATABASE gestion_proyectos;
USE gestion_proyectos;

-- Empleados
CREATE TABLE IF NOT EXISTS empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    cargo VARCHAR(50) NOT NULL
);

-- Proyectos
CREATE TABLE IF NOT EXISTS proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('PLANIFICADO', 'EN_CURSO', 'FINALIZADO') NOT NULL DEFAULT 'PLANIFICADO',
    CONSTRAINT chk_fechas_proyecto CHECK (fecha_fin >= fecha_inicio)
);

-- Asignación de empleados a proyectos (relación N:M)
CREATE TABLE IF NOT EXISTS empleado_proyecto (
    empleado_id INT NOT NULL,
    proyecto_id INT NOT NULL,
    rol VARCHAR(50) NOT NULL,
    fecha_asignacion DATE NOT NULL,
    horas_semanales INT NOT NULL,
    PRIMARY KEY (empleado_id, proyecto_id),
    CONSTRAINT fk_ep_empleado FOREIGN KEY (empleado_id) 
        REFERENCES empleados(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_ep_proyecto FOREIGN KEY (proyecto_id) 
        REFERENCES proyectos(id) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT chk_horas_semanales CHECK (horas_semanales > 0 AND horas_semanales <= 40)
);
