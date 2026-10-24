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
        cargarConfiguracion();
        conectar();
    }

    private static void cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                props.load(input);
                url = props.getProperty("db.url");
                user = props.getProperty("db.user");
                password = props.getProperty("db.password");
            }
        } catch (IOException e) {
            System.err.println("Aviso: No se pudo leer database.properties, usando variables de entorno.");
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
    }

    private void conectar() {
        try {
            this.conexion = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (this.conexion == null || this.conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar estado de la conexion: " + e.getMessage());
        }
        return this.conexion;
    }

    public void cerrarConexion() {
        try {
            if (this.conexion != null && !this.conexion.isClosed()) {
                this.conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexion: " + e.getMessage());
        }
    }
}
