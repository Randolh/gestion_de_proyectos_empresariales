package randolh.gestion_de_proyectos_empresariales.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.model.clases.Empleado;
import randolh.gestion_de_proyectos_empresariales.model.clases.EmpleadoProyecto;
import randolh.gestion_de_proyectos_empresariales.model.clases.EstadoProyecto;
import randolh.gestion_de_proyectos_empresariales.model.clases.Proyecto;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class EmpleadoProyectoDAO {

    public boolean existeAsignacion(int empleadoId, int proyectoId) throws SQLException {
        String sql = "SELECT 1 FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ? LIMIT 1";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public EmpleadoProyecto buscarAsignacion(int empleadoId, int proyectoId) throws SQLException {
        String sql = "SELECT empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EmpleadoProyecto(
                            rs.getInt("empleado_id"),
                            rs.getInt("proyecto_id"),
                            rs.getString("rol"),
                            rs.getDate("fecha_asignacion").toLocalDate(),
                            rs.getInt("horas_semanales")
                    );
                }
            }
        }
        return null;
    }

    public boolean asignar(EmpleadoProyecto ep) throws SQLException {
        String sql = "INSERT INTO empleado_proyecto (empleado_id, proyecto_id, rol, fecha_asignacion, horas_semanales) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ep.getEmpleadoId());
            ps.setInt(2, ep.getProyectoId());
            ps.setString(3, ep.getRol());
            ps.setDate(4, Date.valueOf(ep.getFechaAsignacion()));
            ps.setInt(5, ep.getHorasSemanales());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean modificar(int empleadoId, int proyectoId, String nuevoRol, int nuevasHoras) throws SQLException {
        String sql = "UPDATE empleado_proyecto SET rol = ?, horas_semanales = ? WHERE empleado_id = ? AND proyecto_id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nuevoRol);
            ps.setInt(2, nuevasHoras);
            ps.setInt(3, empleadoId);
            ps.setInt(4, proyectoId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean retirar(int empleadoId, int proyectoId) throws SQLException {
        String sql = "DELETE FROM empleado_proyecto WHERE empleado_id = ? AND proyecto_id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            ps.setInt(2, proyectoId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<EmpleadoProyecto> obtenerIntegrantesProyecto(int proyectoId) throws SQLException {
        String sql = "SELECT ep.empleado_id, ep.proyecto_id, ep.rol, ep.fecha_asignacion, ep.horas_semanales, " +
                     "e.id AS emp_id, e.nombre AS emp_nombre, e.correo AS emp_correo, e.cargo AS emp_cargo " +
                     "FROM empleado_proyecto ep " +
                     "INNER JOIN empleados e ON ep.empleado_id = e.id " +
                     "WHERE ep.proyecto_id = ? " +
                     "ORDER BY ep.fecha_asignacion ASC, e.nombre ASC";

        List<EmpleadoProyecto> lista = new ArrayList<>();
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proyectoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Empleado emp = new Empleado(
                            rs.getInt("emp_id"),
                            rs.getString("emp_nombre"),
                            rs.getString("emp_correo"),
                            rs.getString("emp_cargo")
                    );

                    EmpleadoProyecto ep = new EmpleadoProyecto(
                            rs.getInt("empleado_id"),
                            rs.getInt("proyecto_id"),
                            rs.getString("rol"),
                            rs.getDate("fecha_asignacion").toLocalDate(),
                            rs.getInt("horas_semanales")
                    );
                    ep.setEmpleado(emp);
                    lista.add(ep);
                }
            }
        }
        return lista;
    }

    public List<EmpleadoProyecto> obtenerProyectosPorEmpleado(int empleadoId) throws SQLException {
        String sql = "SELECT ep.empleado_id, ep.proyecto_id, ep.rol, ep.fecha_asignacion, ep.horas_semanales, " +
                     "p.id AS proy_id, p.nombre AS proy_nombre, p.descripcion AS proy_descripcion, " +
                     "p.fecha_inicio AS proy_fecha_inicio, p.fecha_fin AS proy_fecha_fin, p.estado AS proy_estado " +
                     "FROM empleado_proyecto ep " +
                     "INNER JOIN proyectos p ON ep.proyecto_id = p.id " +
                     "WHERE ep.empleado_id = ? " +
                     "ORDER BY ep.fecha_asignacion ASC, p.nombre ASC";

        List<EmpleadoProyecto> lista = new ArrayList<>();
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Proyecto proy = new Proyecto(
                            rs.getInt("proy_id"),
                            rs.getString("proy_nombre"),
                            rs.getString("proy_descripcion"),
                            rs.getDate("proy_fecha_inicio").toLocalDate(),
                            rs.getDate("proy_fecha_fin").toLocalDate(),
                            EstadoProyecto.valueOf(rs.getString("proy_estado"))
                    );

                    EmpleadoProyecto ep = new EmpleadoProyecto(
                            rs.getInt("empleado_id"),
                            rs.getInt("proyecto_id"),
                            rs.getString("rol"),
                            rs.getDate("fecha_asignacion").toLocalDate(),
                            rs.getInt("horas_semanales")
                    );
                    ep.setProyecto(proy);
                    lista.add(ep);
                }
            }
        }
        return lista;
    }
}
