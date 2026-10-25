package randolh.gestion_de_proyectos_empresariales.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.EmpleadoProyecto;
import randolh.gestion_de_proyectos_empresariales.model.dao.EmpleadoDAO;
import randolh.gestion_de_proyectos_empresariales.model.dao.EmpleadoProyectoDAO;
import randolh.gestion_de_proyectos_empresariales.model.dao.ProyectoDAO;

public class EmpleadoProyectoController {

    private final EmpleadoProyectoDAO empleadoProyectoDAO = new EmpleadoProyectoDAO();
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final ProyectoDAO proyectoDAO = new ProyectoDAO();

    public void asignarEmpleadoAProyecto(int empleadoId, int proyectoId, String rol, LocalDate fechaAsignacion, int horasSemanales)
            throws ValidacionException, SQLException {
        validarParametrosAsignacion(empleadoId, proyectoId, rol, fechaAsignacion, horasSemanales);

        if (empleadoDAO.buscarPorId(empleadoId) == null) {
            throw new ValidacionException("No se puede asignar: El empleado con ID " + empleadoId + " no existe.");
        }

        if (proyectoDAO.buscarPorId(proyectoId) == null) {
            throw new ValidacionException("No se puede asignar: El proyecto con ID " + proyectoId + " no existe.");
        }

        if (empleadoProyectoDAO.existeAsignacion(empleadoId, proyectoId)) {
            throw new ValidacionException("El empleado con ID " + empleadoId +
                    " ya se encuentra asignado al proyecto " + proyectoId + ".");
        }

        EmpleadoProyecto ep = new EmpleadoProyecto(
                empleadoId,
                proyectoId,
                rol.trim(),
                fechaAsignacion,
                horasSemanales
        );

        boolean exito = empleadoProyectoDAO.asignar(ep);
        if (!exito) {
            throw new ValidacionException("No se pudo completar la asignación en la base de datos.");
        }
    }

    public void modificarAsignacion(int empleadoId, int proyectoId, String nuevoRol, int nuevasHoras)
            throws ValidacionException, SQLException {
        if (empleadoId <= 0 || proyectoId <= 0) {
            throw new ValidacionException("Los IDs de empleado y proyecto deben ser números positivos.");
        }
        if (nuevoRol == null || nuevoRol.trim().isEmpty()) {
            throw new ValidacionException("El nuevo rol es obligatorio.");
        }
        validarHorasSemanales(nuevasHoras);

        if (!empleadoProyectoDAO.existeAsignacion(empleadoId, proyectoId)) {
            throw new ValidacionException("No existe una asignación activa entre el empleado " +
                    empleadoId + " y el proyecto " + proyectoId + " para modificar.");
        }

        boolean exito = empleadoProyectoDAO.modificar(empleadoId, proyectoId, nuevoRol.trim(), nuevasHoras);
        if (!exito) {
            throw new ValidacionException("No se pudo actualizar la asignación.");
        }
    }

    public void retirarEmpleadoDeProyecto(int empleadoId, int proyectoId) throws ValidacionException, SQLException {
        if (empleadoId <= 0 || proyectoId <= 0) {
            throw new ValidacionException("Los IDs de empleado y proyecto deben ser números positivos.");
        }

        if (!empleadoProyectoDAO.existeAsignacion(empleadoId, proyectoId)) {
            throw new ValidacionException("No se puede retirar: La asignación entre el empleado " +
                    empleadoId + " y el proyecto " + proyectoId + " no existe.");
        }

        boolean exito = empleadoProyectoDAO.retirar(empleadoId, proyectoId);
        if (!exito) {
            throw new ValidacionException("No se pudo retirar la asignación.");
        }
    }

    public List<EmpleadoProyecto> listarIntegrantesProyecto(int proyectoId) throws ValidacionException, SQLException {
        if (proyectoId <= 0) {
            throw new ValidacionException("El ID del proyecto debe ser un número positivo.");
        }

        if (proyectoDAO.buscarPorId(proyectoId) == null) {
            throw new ValidacionException("No existe ningún proyecto con el ID: " + proyectoId);
        }

        return empleadoProyectoDAO.obtenerIntegrantesProyecto(proyectoId);
    }

    public List<EmpleadoProyecto> listarProyectosDeEmpleado(int empleadoId) throws ValidacionException, SQLException {
        if (empleadoId <= 0) {
            throw new ValidacionException("El ID del empleado debe ser un número positivo.");
        }

        if (empleadoDAO.buscarPorId(empleadoId) == null) {
            throw new ValidacionException("No existe ningún empleado con el ID: " + empleadoId);
        }

        return empleadoProyectoDAO.obtenerProyectosPorEmpleado(empleadoId);
    }

    private void validarParametrosAsignacion(int empleadoId, int proyectoId, String rol, LocalDate fechaAsignacion, int horasSemanales)
            throws ValidacionException {
        if (empleadoId <= 0) {
            throw new ValidacionException("El ID del empleado debe ser un número positivo.");
        }
        if (proyectoId <= 0) {
            throw new ValidacionException("El ID del proyecto debe ser un número positivo.");
        }
        if (rol == null || rol.trim().isEmpty()) {
            throw new ValidacionException("El rol del empleado en el proyecto es obligatorio.");
        }
        if (fechaAsignacion == null) {
            throw new ValidacionException("La fecha de asignación es obligatoria.");
        }
        validarHorasSemanales(horasSemanales);
    }

    private void validarHorasSemanales(int horasSemanales) throws ValidacionException {
        if (horasSemanales <= 0 || horasSemanales > 40) {
            throw new ValidacionException("Las horas semanales (" + horasSemanales +
                    ") deben ser mayores a 0 y como máximo 40 horas.");
        }
    }
}
