USE gestion_proyectos;

-- Empleados
INSERT INTO empleados (nombre, correo, cargo) VALUES
('Carlos Mendoza', 'carlos.mendoza@empresa.com', 'Desarrollador Backend'),
('Ana Gómez', 'ana.gomez@empresa.com', 'Diseñadora UI/UX'),
('Roberto Ruiz', 'roberto.ruiz@empresa.com', 'Tech Lead'),
('Laura Morales', 'laura.morales@empresa.com', 'Ingeniera QA'),
('David Pineda', 'david.pineda@empresa.com', 'DevOps Specialist');

-- Proyectos
INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES
('Portal Clientes Web', 'Plataforma web de autoservicio y facturación electrónica', '2026-01-15', '2026-06-30', 'EN_CURSO'),
('App Móvil B2B', 'Aplicación móvil multiplataforma para pedidos de distribuidores', '2026-03-01', '2026-11-30', 'PLANIFICADO'),
('Migración Cloud AWS', 'Migración de servidores físicos heredados a contenedores ECS', '2025-08-01', '2025-12-20', 'FINALIZADO');

-- Asignaciones
INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) VALUES
(1, 1, 'Desarrollador Java / Spring Boot', '2026-01-20', 20),
(2, 1, 'Diseñadora Principal Figma', '2026-01-20', 15),
(3, 1, 'Scrum Master y Arquitecto', '2026-01-15', 10),
(1, 2, 'Desarrollador Backend API REST', '2026-03-05', 15),
(4, 2, 'Líder de Calidad y Testing', '2026-03-05', 25);
