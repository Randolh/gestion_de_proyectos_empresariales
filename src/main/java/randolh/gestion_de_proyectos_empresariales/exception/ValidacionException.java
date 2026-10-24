package randolh.gestion_de_proyectos_empresariales.exception;

public class ValidacionException extends Exception {

    public ValidacionException(String mensaje) {
        super(mensaje);
    }

    public ValidacionException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
