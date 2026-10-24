package randolh.gestion_de_proyectos_empresariales.model.clases;

import java.util.Objects;

public class Empleado {

    private int id;
    private String nombre;
    private String correo;
    private String cargo;

    public Empleado() {
    }

    public Empleado(String nombre, String correo, String cargo) {
        this.nombre = nombre;
        this.correo = correo;
        this.cargo = cargo;
    }

    public Empleado(int id, String nombre, String correo, String cargo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        return id == empleado.id || Objects.equals(correo, empleado.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, correo);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | Correo: %s | Cargo: %s", id, nombre, correo, cargo);
    }
}
