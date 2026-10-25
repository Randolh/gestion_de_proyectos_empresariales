package randolh.gestion_de_proyectos_empresariales.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.controller.EmpleadoProyectoController;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.EmpleadoProyecto;

public class AsignacionView {

    private final EmpleadoProyectoController controlador = new EmpleadoProyectoController();

    public void mostrarMenuAsignaciones() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE ASIGNACIONES ---");
            System.out.println("1. Asignar empleado a proyecto");
            System.out.println("2. Modificar rol u horas de asignación");
            System.out.println("3. Retirar empleado de proyecto");
            System.out.println("0. Volver al menú principal");

            int opcion = ConsolaUtils.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    asignar();
                    break;
                case 2:
                    modificar();
                    break;
                case 3:
                    retirar();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void asignar() {
        System.out.println("\n-- Asignar Empleado a Proyecto --");
        int empId = ConsolaUtils.leerEntero("ID del empleado: ");
        int proyId = ConsolaUtils.leerEntero("ID del proyecto: ");
        String rol = ConsolaUtils.leerTextoNoVacio("Rol en el proyecto (ej: Desarrollador, QA, Líder): ");
        LocalDate fechaAsignacion = ConsolaUtils.leerFecha("Fecha de asignación");
        int horas = ConsolaUtils.leerEntero("Horas semanales dedicadas (1 a 40): ");

        try {
            controlador.asignarEmpleadoAProyecto(empId, proyId, rol, fechaAsignacion, horas);
            System.out.println("¡Empleado asignado al proyecto exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void modificar() {
        System.out.println("\n-- Modificar Asignación --");
        int empId = ConsolaUtils.leerEntero("ID del empleado: ");
        int proyId = ConsolaUtils.leerEntero("ID del proyecto: ");
        String nuevoRol = ConsolaUtils.leerTextoNoVacio("Nuevo rol en el proyecto: ");
        int nuevasHoras = ConsolaUtils.leerEntero("Nuevas horas semanales (1 a 40): ");

        try {
            controlador.modificarAsignacion(empId, proyId, nuevoRol, nuevasHoras);
            System.out.println("¡Asignación modificada exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void retirar() {
        System.out.println("\n-- Retirar Empleado de Proyecto --");
        int empId = ConsolaUtils.leerEntero("ID del empleado: ");
        int proyId = ConsolaUtils.leerEntero("ID del proyecto: ");

        try {
            controlador.retirarEmpleadoDeProyecto(empId, proyId);
            System.out.println("¡Empleado retirado del proyecto exitosamente!");
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    public void mostrarMenuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- CONSULTAS Y REPORTES ---");
            System.out.println("1. Listar integrantes de un proyecto");
            System.out.println("2. Listar proyectos en los que participa un empleado");
            System.out.println("0. Volver al menú principal");

            int opcion = ConsolaUtils.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    reporteIntegrantesProyecto();
                    break;
                case 2:
                    reporteProyectosDeEmpleado();
                    break;
                case 0:
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void reporteIntegrantesProyecto() {
        System.out.println("\n-- Integrantes de un Proyecto --");
        int proyId = ConsolaUtils.leerEntero("ID del proyecto a consultar: ");

        try {
            List<EmpleadoProyecto> integrantes = controlador.listarIntegrantesProyecto(proyId);
            if (integrantes.isEmpty()) {
                System.out.println("El proyecto no tiene empleados asignados actualmente.");
            } else {
                System.out.println("Integrantes asignados (" + integrantes.size() + "):");
                for (EmpleadoProyecto ep : integrantes) {
                    System.out.println(" - " + ep);
                }
            }
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }

    private void reporteProyectosDeEmpleado() {
        System.out.println("\n-- Proyectos de un Empleado --");
        int empId = ConsolaUtils.leerEntero("ID del empleado a consultar: ");

        try {
            List<EmpleadoProyecto> proyectos = controlador.listarProyectosDeEmpleado(empId);
            if (proyectos.isEmpty()) {
                System.out.println("El empleado no se encuentra asignado a ningún proyecto actualmente.");
            } else {
                System.out.println("Proyectos asignados (" + proyectos.size() + "):");
                for (EmpleadoProyecto ep : proyectos) {
                    System.out.println(" - " + ep);
                }
            }
        } catch (ValidacionException e) {
            System.out.println("[AVISO]: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[ERROR BD]: " + e.getMessage());
        }
    }
}
