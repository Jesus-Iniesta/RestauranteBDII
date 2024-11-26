package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.sql.*;

public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String telefono;
    private Direccion direccion;  // Suponiendo que Direccion es otra entidad o tipo de datos

    // Constructor
    public Proveedor(int idProveedor, String nombre, String telefono, Direccion direccion) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public Proveedor() {
    }
    
    // Getters y setters
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
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
    
    public void setDireccion(String direccionString) {
        
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());
        
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Proveedor [idProveedor=" + idProveedor + ", nombre=" + nombre + ", telefono=" + telefono +
                ", direccion=" + direccion + "]";
    }
}

