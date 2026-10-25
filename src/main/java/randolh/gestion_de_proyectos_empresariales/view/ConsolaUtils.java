package randolh.gestion_de_proyectos_empresariales.view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsolaUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero válido.");
            }
        }
    }

    public static String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Error: Este campo no puede estar vacío.");
        }
    }

    public static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (formato AAAA-MM-DD): ");
            String texto = scanner.nextLine().trim();
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Formato de fecha inválido. Utilice el formato AAAA-MM-DD (ejemplo: 2026-05-15).");
            }
        }
    }
}
