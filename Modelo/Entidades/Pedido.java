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

public class Pedido {
    private int idPedido;
    private double precio;
    private int cantidad;
    private String tipo;
    private String productos;
    private String metodoPago;
    private int idVenta;
    private int idProducto;
    private Date fechaVenta;

    // Constructor
    public Pedido(int idPedido, double precio, int cantidad, String tipo, String productos, String metodoPago, 
                  int idVenta, int idProducto, Date fechaVenta) {
        this.idPedido = idPedido;
        this.precio = precio;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.productos = productos;
        this.metodoPago = metodoPago;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.fechaVenta = fechaVenta;
    }

    // Getters y setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido + ", precio=" + precio + ", cantidad=" + cantidad + ", tipo=" + tipo +
               ", productos=" + productos + ", metodoPago=" + metodoPago + ", idVenta=" + idVenta + 
               ", idProducto=" + idProducto + ", fechaVenta=" + fechaVenta + "]";
    }
}
