package randolh.gestion_de_proyectos_empresariales;

import java.sql.Connection;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class Main {

    public static void main(String[] args) {
        System.out.println("Verificando conexion con la base de datos...");
        Connection conn = ConexionBD.getInstancia().getConexion();
        
        if (conn != null) {
            System.out.println("¡Conexion exitosa con MySQL!");
        } else {
            System.err.println("No se pudo establecer conexion con la base de datos.");
        }
    }
}
