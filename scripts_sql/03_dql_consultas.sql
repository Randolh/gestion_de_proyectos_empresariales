USE gestion_proyectos;

-- Integrantes de un proyecto (RF-08)
SELECT 
    p.nombre AS proyecto,
    p.estado,
    e.id AS empleado_id,
    e.nombre AS empleado,
    e.correo,
    e.cargo,
    ep.rol AS rol_en_proyecto,
    ep.fecha_asignacion,
    ep.horas_semanales
FROM empleado_proyecto ep
JOIN empleados e ON ep.empleado_id = e.id
JOIN proyectos p ON ep.proyecto_id = p.id
WHERE p.id = 1
ORDER BY ep.horas_semanales DESC;

-- Proyectos de un empleado (RF-09)
SELECT 
    e.nombre AS empleado,
    e.cargo,
    p.id AS proyecto_id,
    p.nombre AS proyecto,
    p.estado AS estado_proyecto,
    ep.rol AS rol_en_proyecto,
    ep.fecha_asignacion,
    ep.horas_semanales
FROM empleado_proyecto ep
JOIN proyectos p ON ep.proyecto_id = p.id
JOIN empleados e ON ep.empleado_id = e.id
WHERE e.id = 1
ORDER BY p.fecha_inicio ASC;
