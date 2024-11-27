/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.sql.Date;

public class Almacen {
    private int idAlmacen;
    private String nombre;
    private Date fechaCaducidad;  // Tipo de dato SQL Date
    private int stock;

    // Constructor
    public Almacen(int idAlmacen, String nombre, Date fechaCaducidad, int stock) {
        this.idAlmacen = idAlmacen;
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.stock = stock;
    }

    // Getters y setters
    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Almacen [idAlmacen=" + idAlmacen + ", nombre=" + nombre + ", fechaCaducidad=" + fechaCaducidad + ", stock=" + stock + "]";
    }
}

