/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class DetalleAlmacen {
    private int id_Detalle;
    private int id_Almacen;
    private int id_Producto;
    private int cantidad_Utilizada;

    // Constructor
    public DetalleAlmacen(int idDetalle, int idAlmacen, int idProducto, int cantidadUtilizada) {
        this.id_Detalle = idDetalle;
        this.id_Almacen = idAlmacen;
        this.id_Producto = idProducto;
        this.cantidad_Utilizada = cantidadUtilizada;
    }

    // Getters y setters
    public int getIdDetalle() {
        return id_Detalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.id_Detalle = idDetalle;
    }

    public int getIdAlmacen() {
        return id_Almacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.id_Almacen = idAlmacen;
    }

    public int getIdProducto() {
        return id_Producto;
    }

    public void setIdProducto(int idProducto) {
        this.id_Producto = idProducto;
    }

    public int getCantidadUtilizada() {
        return cantidad_Utilizada;
    }

    public void setCantidadUtilizada(int cantidadUtilizada) {
        this.cantidad_Utilizada = cantidadUtilizada;
    }

    @Override
    public String toString() {
        return "Detalle_Almacen [id_Detalle=" + id_Detalle + ", id_Almacen=" + id_Almacen + ", id_Producto=" + id_Producto +
                ", cantidad_Utilizada=" + cantidad_Utilizada + "]";
    }
}
