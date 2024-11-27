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
    private int[] cantidad;
    private String tipo;
    private String metodoPago;
    private int idVenta;
    private int[] idProducto;
    private Date fechaVenta;

    // Constructor

    public Pedido(int idPedido, int[] cantidad, String tipo, String metodoPago, int idVenta, int[] idProducto, Date fechaVenta) {
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.tipo = tipo;
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
    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int[] getCantidad() {
        return cantidad;
    }

    public void setCantidad(int[] cantidad) {
        this.cantidad = cantidad;
    }

    public int[] getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int[] idProducto) {
        this.idProducto = idProducto;
    }
    

    @Override
    public String toString() {
        return "Pedido [idPedido=" + idPedido +  ", cantidad=" + cantidad + ", tipo=" + tipo +
                ", metodoPago=" + metodoPago + ", idVenta=" + idVenta + 
               ", idProducto=" + idProducto + ", fechaVenta=" + fechaVenta + "]";
    }
}
