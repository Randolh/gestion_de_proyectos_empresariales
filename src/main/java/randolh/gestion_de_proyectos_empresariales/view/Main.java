package randolh.gestion_de_proyectos_empresariales.view;

import java.sql.SQLException;
import randolh.gestion_de_proyectos_empresariales.model.config.ConexionBD;

public class Main {

    public static void main(String[] args) {
        try {
            MenuPrincipal menu = new MenuPrincipal();
            menu.iniciar();
        } finally {
            try {
                ConexionBD.getInstancia().cerrarConexion();
            } catch (SQLException ignored) {
            }
        }
    }
}
