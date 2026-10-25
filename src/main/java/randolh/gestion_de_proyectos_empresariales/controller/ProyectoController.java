package randolh.gestion_de_proyectos_empresariales.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.EstadoProyecto;
import randolh.gestion_de_proyectos_empresariales.model.clases.Proyecto;
import randolh.gestion_de_proyectos_empresariales.model.dao.ProyectoDAO;

public class ProyectoController {

    private final ProyectoDAO proyectoDAO = new ProyectoDAO();

    public void registrarProyecto(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String estadoTexto)
            throws ValidacionException, SQLException {
        validarCamposProyecto(nombre, fechaInicio, fechaFin);
        EstadoProyecto estado = EstadoProyecto.fromString(estadoTexto);

        Proyecto nuevo = new Proyecto(
                nombre.trim(),
                descripcion != null ? descripcion.trim() : "",
                fechaInicio,
                fechaFin,
                estado
        );

        boolean registrado = proyectoDAO.registrar(nuevo);
        if (!registrado) {
            throw new ValidacionException("No se pudo registrar el proyecto en la base de datos.");
        }
    }

    public List<Proyecto> listarProyectos() throws SQLException {
        return proyectoDAO.listarTodos();
    }

    public Proyecto buscarProyectoPorId(int id) throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del proyecto debe ser un número positivo.");
        }

        Proyecto proyecto = proyectoDAO.buscarPorId(id);
        if (proyecto == null) {
            throw new ValidacionException("No se encontró ningún proyecto con el ID: " + id);
        }
        return proyecto;
    }

    public void actualizarProyecto(int id, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String estadoTexto)
            throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del proyecto debe ser un número positivo.");
        }
        validarCamposProyecto(nombre, fechaInicio, fechaFin);
        EstadoProyecto estado = EstadoProyecto.fromString(estadoTexto);

        buscarProyectoPorId(id);

        Proyecto actualizado = new Proyecto(
                id,
                nombre.trim(),
                descripcion != null ? descripcion.trim() : "",
                fechaInicio,
                fechaFin,
                estado
        );

        boolean exito = proyectoDAO.actualizar(actualizado);
        if (!exito) {
            throw new ValidacionException("No se pudo actualizar el proyecto con ID: " + id);
        }
    }

    public void eliminarProyecto(int id) throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del proyecto debe ser un número positivo.");
        }

        buscarProyectoPorId(id);

        if (proyectoDAO.tieneAsignaciones(id)) {
            throw new ValidacionException("No se puede eliminar el proyecto con ID " + id +
                    " porque tiene integrantes asignados. Primero retire las asignaciones correspondientes.");
        }

        boolean eliminado = proyectoDAO.eliminar(id);
        if (!eliminado) {
            throw new ValidacionException("No se pudo eliminar el proyecto con ID: " + id);
        }
    }

    private void validarCamposProyecto(String nombre, LocalDate fechaInicio, LocalDate fechaFin) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del proyecto es obligatorio.");
        }
        if (fechaInicio == null) {
            throw new ValidacionException("La fecha de inicio del proyecto es obligatoria.");
        }
        if (fechaFin == null) {
            throw new ValidacionException("La fecha de fin del proyecto es obligatoria.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new ValidacionException("La fecha de fin (" + fechaFin + ") no puede ser anterior a la fecha de inicio (" + fechaInicio + ").");
        }
    }
}
