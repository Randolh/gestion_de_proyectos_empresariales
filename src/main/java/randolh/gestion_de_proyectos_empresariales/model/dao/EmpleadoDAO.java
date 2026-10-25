package randolh.gestion_de_proyectos_empresariales.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.model.clases.Empleado;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class EmpleadoDAO {

    public boolean registrar(Empleado e) throws SQLException {
        String sql = "INSERT INTO empleados (nombre, correo, cargo) VALUES (?, ?, ?)";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setString(3, e.getCargo());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        e.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public List<Empleado> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre, correo, cargo FROM empleados ORDER BY id ASC";
        List<Empleado> lista = new ArrayList<>();
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("cargo")
                );
                lista.add(emp);
            }
        }
        return lista;
    }

    public Empleado buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, correo, cargo FROM empleados WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("cargo")
                    );
                }
            }
        }
        return null;
    }

    public boolean existePorCorreo(String correo) throws SQLException {
        String sql = "SELECT 1 FROM empleados WHERE LOWER(correo) = LOWER(?) LIMIT 1";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean actualizar(Empleado e) throws SQLException {
        String sql = "UPDATE empleados SET nombre = ?, correo = ?, cargo = ? WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getCorreo());
            ps.setString(3, e.getCargo());
            ps.setInt(4, e.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tieneAsignaciones(int empleadoId) throws SQLException {
        String sql = "SELECT 1 FROM empleado_proyecto WHERE empleado_id = ? LIMIT 1";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empleadoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM empleados WHERE id = ?";
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
