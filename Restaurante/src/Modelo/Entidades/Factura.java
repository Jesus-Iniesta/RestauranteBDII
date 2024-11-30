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

public class Factura {
    private int idFactura;
    private String rfcCliente;
    private Date fechaExpedicion;
    private int idCliente;  // Clave foránea que hace referencia a 'cliente'
    private int idPedido;
    private String nombre_cliente;

    // Constructor

    public Factura(String rfcCliente, Date fechaExpedicion, int idCliente, int idPedido, String nombre_cliente) {
        this.rfcCliente = rfcCliente;
        this.fechaExpedicion = fechaExpedicion;
        this.idCliente = idCliente;
        this.idPedido = idPedido;
        this.nombre_cliente = nombre_cliente;
    }

    


    // Getters y setters
    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getRfcCliente() {
        return rfcCliente;
    }

    public void setRfcCliente(String rfcCliente) {
        this.rfcCliente = rfcCliente;
    }




    public Date getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(Date fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
    

    @Override
    public String toString() {
    return "Factura{" +
           "idFactura=" + idFactura +
           ", rfcCliente='" + rfcCliente + '\'' +
           ", nombre_cliente='" + nombre_cliente + '\'' +
           ", fechaExpedicion=" + fechaExpedicion +
           ", idCliente=" + idCliente +
           ", idPedido=" + idPedido +
           '}';
}
}

