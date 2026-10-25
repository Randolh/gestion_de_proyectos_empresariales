package randolh.gestion_de_proyectos_empresariales.controller;

import java.sql.SQLException;
import java.util.List;
import randolh.gestion_de_proyectos_empresariales.exception.ValidacionException;
import randolh.gestion_de_proyectos_empresariales.model.clases.Empleado;
import randolh.gestion_de_proyectos_empresariales.model.dao.EmpleadoDAO;

public class EmpleadoController {

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public void registrarEmpleado(String nombre, String correo, String cargo) throws ValidacionException, SQLException {
        validarCamposEmpleado(nombre, correo, cargo);

        if (empleadoDAO.existePorCorreo(correo.trim())) {
            throw new ValidacionException("Ya existe un empleado con el correo: " + correo.trim() + ".");
        }

        Empleado nuevo = new Empleado(nombre.trim(), correo.trim(), cargo.trim());
        boolean registrado = empleadoDAO.registrar(nuevo);
        if (!registrado) {
            throw new ValidacionException("No se pudo registrar el empleado en la base de datos.");
        }
    }

    public List<Empleado> listarEmpleados() throws SQLException {
        return empleadoDAO.listarTodos();
    }

    public Empleado buscarEmpleadoPorId(int id) throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del empleado debe ser un número positivo.");
        }

        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) {
            throw new ValidacionException("No se encontró ningún empleado con el ID: " + id);
        }
        return empleado;
    }

    public void actualizarEmpleado(int id, String nombre, String correo, String cargo) throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del empleado debe ser un número positivo.");
        }
        validarCamposEmpleado(nombre, correo, cargo);

        Empleado actual = buscarEmpleadoPorId(id);

        if (!actual.getCorreo().equalsIgnoreCase(correo.trim()) && empleadoDAO.existePorCorreo(correo.trim())) {
            throw new ValidacionException("El nuevo correo ya está registrado por otro empleado.");
        }

        Empleado actualizado = new Empleado(id, nombre.trim(), correo.trim(), cargo.trim());
        boolean exito = empleadoDAO.actualizar(actualizado);
        if (!exito) {
            throw new ValidacionException("No se pudo actualizar el empleado con ID: " + id);
        }
    }

    public void eliminarEmpleado(int id) throws ValidacionException, SQLException {
        if (id <= 0) {
            throw new ValidacionException("El ID del empleado debe ser un número positivo.");
        }

        buscarEmpleadoPorId(id);

        if (empleadoDAO.tieneAsignaciones(id)) {
            throw new ValidacionException("No se puede eliminar el empleado con ID " + id +
                    " porque tiene asignaciones activas en proyectos. Primero retírelo de sus proyectos.");
        }

        boolean eliminado = empleadoDAO.eliminar(id);
        if (!eliminado) {
            throw new ValidacionException("No se pudo eliminar el empleado con ID: " + id);
        }
    }

    private void validarCamposEmpleado(String nombre, String correo, String cargo) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre del empleado es obligatorio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new ValidacionException("El correo del empleado es obligatorio.");
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            throw new ValidacionException("El formato del correo electrónico no es válido.");
        }
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new ValidacionException("El cargo del empleado es obligatorio.");
        }
    }
}
