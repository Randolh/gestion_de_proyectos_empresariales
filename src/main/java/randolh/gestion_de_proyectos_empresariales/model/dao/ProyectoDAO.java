package randolh.gestion_de_proyectos_empresariales.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import randolh.gestion_de_proyectos_empresariales.model.clases.EstadoProyecto;
import randolh.gestion_de_proyectos_empresariales.model.clases.Proyecto;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class ProyectoDAO {

    public boolean registrar(Proyecto p) throws SQLException {
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setString(5, p.getEstado().name());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public List<Proyecto> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyectos ORDER BY id ASC";
        List<Proyecto> lista = new ArrayList<>();
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proyecto proy = new Proyecto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        EstadoProyecto.valueOf(rs.getString("estado"))
                );
                lista.add(proy);
            }
        }
        return lista;
    }

    public Optional<Proyecto> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyectos WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proyecto proy = new Proyecto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            rs.getDate("fecha_fin").toLocalDate(),
                            EstadoProyecto.valueOf(rs.getString("estado"))
                    );
                    return Optional.of(proy);
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(Proyecto p) throws SQLException {
        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDate(3, Date.valueOf(p.getFechaInicio()));
            ps.setDate(4, Date.valueOf(p.getFechaFin()));
            ps.setString(5, p.getEstado().name());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int proyectoId) throws SQLException {
        String sql = "SELECT 1 FROM empleado_proyecto WHERE proyecto_id = ? LIMIT 1";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, proyectoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proyectos WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
