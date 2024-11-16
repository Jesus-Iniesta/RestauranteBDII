/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.math.BigDecimal;

public class Productos {
    private int idProductos;
    private String nombre;
    private BigDecimal precio;
    private int stock;
    private int idProveedor;  // Clave foránea que hace referencia a 'proveedor'

    // Constructor
    public Productos(int idProductos, String nombre, BigDecimal precio, int stock, int idProveedor) {
        this.idProductos = idProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    // Getters y setters
    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public String toString() {
        return "Productos [idProductos=" + idProductos + ", nombre=" + nombre + ", precio=" + precio + 
                ", stock=" + stock + ", idProveedor=" + idProveedor + "]";
    }
}

