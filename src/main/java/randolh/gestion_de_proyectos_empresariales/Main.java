package randolh.gestion_de_proyectos_empresariales;

import java.sql.Connection;
import java.sql.SQLException;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class Main {

    public static void main(String[] args) {
        System.out.println("Verificando conexion con la base de datos...");
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            if (conn != null && !conn.isClosed()) {
                System.out.println("¡Conexion exitosa con MySQL!");
            }
            ConexionBD.getInstancia().cerrarConexion();
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}
