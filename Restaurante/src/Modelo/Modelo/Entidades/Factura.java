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
    private String telefonoCliente;
    private String nombreCliente;
    private Date fechaExpedicion;
    private int idCliente;  // Clave foránea que hace referencia a 'cliente'

    // Constructor
    public Factura(int idFactura, String rfcCliente, String telefonoCliente, String nombreCliente, Date fechaExpedicion, int idCliente) {
        this.idFactura = idFactura;
        this.rfcCliente = rfcCliente;
        this.telefonoCliente = telefonoCliente;
        this.nombreCliente = nombreCliente;
        this.fechaExpedicion = fechaExpedicion;
        this.idCliente = idCliente;
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

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    @Override
    public String toString() {
        return "Factura [idFactura=" + idFactura + ", rfcCliente=" + rfcCliente + ", telefonoCliente=" + telefonoCliente +
                ", nombreCliente=" + nombreCliente + ", fechaExpedicion=" + fechaExpedicion + ", idCliente=" + idCliente + "]";
    }
}

