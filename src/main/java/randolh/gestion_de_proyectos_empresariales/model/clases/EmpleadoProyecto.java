package randolh.gestion_de_proyectos_empresariales.model.clases;

import java.time.LocalDate;
import java.util.Objects;

public class EmpleadoProyecto {

    private int empleadoId;
    private int proyectoId;
    private String rol;
    private LocalDate fechaAsignacion;
    private int horasSemanales;

    // Asociación POO
    private Empleado empleado;
    private Proyecto proyecto;

    public EmpleadoProyecto() {
    }

    public EmpleadoProyecto(int empleadoId, int proyectoId, String rol, LocalDate fechaAsignacion, int horasSemanales) {
        this.empleadoId = empleadoId;
        this.proyectoId = proyectoId;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }

    public EmpleadoProyecto(Empleado empleado, Proyecto proyecto, String rol, LocalDate fechaAsignacion, int horasSemanales) {
        this.empleado = empleado;
        this.proyecto = proyecto;
        this.empleadoId = (empleado != null) ? empleado.getId() : 0;
        this.proyectoId = (proyecto != null) ? proyecto.getId() : 0;
        this.rol = rol;
        this.fechaAsignacion = fechaAsignacion;
        this.horasSemanales = horasSemanales;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public int getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(int proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(int horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
        if (empleado != null) {
            this.empleadoId = empleado.getId();
        }
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        if (proyecto != null) {
            this.proyectoId = proyecto.getId();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoProyecto that = (EmpleadoProyecto) o;
        return empleadoId == that.empleadoId && proyectoId == that.proyectoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empleadoId, proyectoId);
    }

    @Override
    public String toString() {
        if (empleado != null && proyecto != null) {
            return String.format("Empleado: %s | Proyecto: %s | Rol: %s | Horas: %d hrs/sem | Asignado: %s",
                    empleado.getNombre(), proyecto.getNombre(), rol, horasSemanales, fechaAsignacion);
        } else if (empleado != null) {
            return String.format("[%d] %s (Cargo: %s) | Rol: %s | Horas: %d hrs/sem | Asignado: %s",
                    empleado.getId(), empleado.getNombre(), empleado.getCargo(), rol, horasSemanales, fechaAsignacion);
        } else if (proyecto != null) {
            return String.format("[%d] %s (Estado: %s) | Rol: %s | Horas: %d hrs/sem | Asignado: %s",
                    proyecto.getId(), proyecto.getNombre(), proyecto.getEstado(), rol, horasSemanales, fechaAsignacion);
        } else {
            return String.format("EmpleadoID: %d | ProyectoID: %d | Rol: %s | Horas: %d hrs/sem | Asignado: %s",
                    empleadoId, proyectoId, rol, horasSemanales, fechaAsignacion);
        }
    }
}
