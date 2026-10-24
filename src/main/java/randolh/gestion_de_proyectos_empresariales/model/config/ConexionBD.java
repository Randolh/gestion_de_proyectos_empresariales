package randolh.gestion_de_proyectos_empresariales.model.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static String url;
    private static String user;
    private static String password;

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        // Constructor privado para el patrón Singleton
    }

    private static synchronized void cargarConfiguracion() throws SQLException {
        if (url != null) {
            return;
        }

        Properties props = new Properties();
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                props.load(input);
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            }
        } catch (IOException e) {
            throw new SQLException("Error al leer el archivo de configuracion database.properties", e);
        }

        // Si existen variables de entorno, tienen prioridad
        if (System.getenv("DB_URL") != null) {
            url = System.getenv("DB_URL");
        }
        if (System.getenv("DB_USER") != null) {
            user = System.getenv("DB_USER");
        }
        if (System.getenv("DB_PASSWORD") != null) {
            password = System.getenv("DB_PASSWORD");
        }

        if (url == null || url.trim().isEmpty()) {
            throw new SQLException("No se encontro configuracion para la base de datos (verifique database.properties o DB_URL).");
        }
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() throws SQLException {
        if (url == null) {
            cargarConfiguracion();
        }
        if (this.conexion == null || this.conexion.isClosed()) {
            this.conexion = DriverManager.getConnection(url, user, password);
        }
        return this.conexion;
    }

    public void cerrarConexion() throws SQLException {
        if (this.conexion != null && !this.conexion.isClosed()) {
            this.conexion.close();
        }
    }
}
