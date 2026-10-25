package randolh.gestion_de_proyectos_empresariales.view;

import java.sql.SQLException;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.controller.EmpleadoController;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.Empleado;

public class EmpleadoView {

    private final EmpleadoController controlador = new EmpleadoController();

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
            System.out.println("1. Registrar nuevo empleado");
            System.out.println("2. Listar todos los empleados");
            System.out.println("3. Actualizar datos de empleado");
            System.out.println("4. Eliminar empleado");
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
        System.out.println("\n-- Registrar Empleado --");
        String nombre = ConsolaUtils.leerTextoNoVacio("Nombre completo: ");
        String correo = ConsolaUtils.leerTextoNoVacio("Correo electrónico: ");
        String cargo = ConsolaUtils.leerTextoNoVacio("Cargo: ");

        try {
            controlador.registrarEmpleado(nombre, correo, cargo);
            System.out.println("¡Empleado registrado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\n-- Lista de Empleados --");
        try {
            List<Empleado> lista = controlador.listarEmpleados();
            if (lista.isEmpty()) {
                System.out.println("No hay empleados registrados en el sistema.");
            } else {
                for (Empleado e : lista) {
                    System.out.println(e);
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void actualizar() {
        System.out.println("\n-- Actualizar Empleado --");
        int id = ConsolaUtils.leerEntero("Ingrese el ID del empleado a actualizar: ");
        String nombre = ConsolaUtils.leerTextoNoVacio("Nuevo nombre completo: ");
        String correo = ConsolaUtils.leerTextoNoVacio("Nuevo correo electrónico: ");
        String cargo = ConsolaUtils.leerTextoNoVacio("Nuevo cargo: ");

        try {
            controlador.actualizarEmpleado(id, nombre, correo, cargo);
            System.out.println("¡Empleado actualizado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void eliminar() {
        System.out.println("\n-- Eliminar Empleado --");
        int id = ConsolaUtils.leerEntero("Ingrese el ID del empleado a eliminar: ");

        try {
            controlador.eliminarEmpleado(id);
            System.out.println("¡Empleado eliminado exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }
}
