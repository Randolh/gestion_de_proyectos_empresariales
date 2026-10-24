package randolh.gestion_de_proyectos_empresariales.model.clases;

import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;

public enum EstadoProyecto {
    PLANIFICADO,
    EN_CURSO,
    FINALIZADO;

    public static EstadoProyecto fromString(String valor) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El estado del proyecto es obligatorio.");
        }
        
        String normalizado = valor.trim().toUpperCase().replace(" ", "_").replace("-", "_");
        for (EstadoProyecto estado : values()) {
            if (estado.name().equals(normalizado)) {
                return estado;
            }
        }
        
        throw new ValidacionException("Estado '" + valor + "' inválido. Debe ser: PLANIFICADO, EN_CURSO o FINALIZADO.");
    }
}
