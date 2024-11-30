
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class Restaurante {
    private int idRestaurante;
    private String nombre;
    private String telefono;
    private Direccion direccion;

    // Constructor
    public Restaurante(int idRestaurante, String nombre, String telefono, Direccion direccion) {
        this.idRestaurante = idRestaurante;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Restaurante() {
    }
    

    // Getters y setters
    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
               "idRestaurante=" + idRestaurante +
               ", nombre='" + nombre + '\'' +
               ", telefono='" + telefono + '\'' +
               ", direccion=" + direccion +
               '}';
    }
}
