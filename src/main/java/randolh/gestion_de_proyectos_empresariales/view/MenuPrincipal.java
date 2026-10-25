package randolh.gestion_de_proyectos_empresariales.view;

public class MenuPrincipal {

    private final EmpleadoView empleadoView = new EmpleadoView();
    private final ProyectoView proyectoView = new ProyectoView();
    private final AsignacionView asignacionView = new AsignacionView();

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n==================================================");
            System.out.println("   SISTEMA DE GESTIÓN DE PROYECTOS EMPRESARIALES  ");
            System.out.println("==================================================");
            System.out.println("1. Gestión de Empleados");
            System.out.println("2. Gestión de Proyectos");
            System.out.println("3. Gestión de Asignaciones");
            System.out.println("4. Consultas y Reportes");
            System.out.println("0. Salir del Sistema");
            System.out.println("--------------------------------------------------");

            int opcion = ConsolaUtils.leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1:
                    empleadoView.mostrarMenu();
                    break;
                case 2:
                    proyectoView.mostrarMenu();
                    break;
                case 3:
                    asignacionView.mostrarMenuAsignaciones();
                    break;
                case 4:
                    asignacionView.mostrarMenuReportes();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\nCerrando el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
