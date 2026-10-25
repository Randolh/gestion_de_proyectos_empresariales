package randolh.gestion_de_proyectos_empresariales.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.controller.ProyectoController;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.Proyecto;

public class ProyectoView {

    private final ProyectoController controlador = new ProyectoController();

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PROYECTOS ---");
            System.out.println("1. Registrar nuevo proyecto");
            System.out.println("2. Listar todos los proyectos");
            System.out.println("3. Actualizar datos de proyecto");
            System.out.println("4. Eliminar proyecto");
            System.out.println("0. Volver al menú principal");

            int opcion = ConsolaUtils.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    registrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    actualizar();
                    break;
                case 4:
                    eliminar();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void registrar() {
        System.out.println("\n-- Registrar Proyecto --");
        String nombre = ConsolaUtils.leerTextoNoVacio("Nombre del proyecto: ");
        String descripcion = ConsolaUtils.leerTextoOpcional("Descripción (opcional): ");
        LocalDate fechaInicio = ConsolaUtils.leerFecha("Fecha de inicio");
        LocalDate fechaFin = ConsolaUtils.leerFecha("Fecha de fin");
        String estado = ConsolaUtils.leerTextoNoVacio("Estado (PLANIFICADO / EN_CURSO / FINALIZADO): ");

        try {
            controlador.registrarProyecto(nombre, descripcion, fechaInicio, fechaFin, estado);
            System.out.println("¡Proyecto registrado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n-- Lista de Proyectos --");
        try {
            List<Proyecto> lista = controlador.listarProyectos();
            if (lista.isEmpty()) {
                System.out.println("No hay proyectos registrados en el sistema.");
            } else {
                for (Proyecto p : lista) {
                    System.out.println(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void actualizar() {
        System.out.println("\n-- Actualizar Proyecto --");
        int id = ConsolaUtils.leerEntero("Ingrese el ID del proyecto a actualizar: ");
        String nombre = ConsolaUtils.leerTextoNoVacio("Nuevo nombre del proyecto: ");
        String descripcion = ConsolaUtils.leerTextoOpcional("Nueva descripción: ");
        LocalDate fechaInicio = ConsolaUtils.leerFecha("Nueva fecha de inicio");
        LocalDate fechaFin = ConsolaUtils.leerFecha("Nueva fecha de fin");
        String estado = ConsolaUtils.leerTextoNoVacio("Nuevo estado (PLANIFICADO / EN_CURSO / FINALIZADO): ");

        try {
            controlador.actualizarProyecto(id, nombre, descripcion, fechaInicio, fechaFin, estado);
            System.out.println("¡Proyecto actualizado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void eliminar() {
        System.out.println("\n-- Eliminar Proyecto --");
        int id = ConsolaUtils.leerEntero("Ingrese el ID del proyecto a eliminar: ");

        try {
            controlador.eliminarProyecto(id);
            System.out.println("¡Proyecto eliminado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }
}
