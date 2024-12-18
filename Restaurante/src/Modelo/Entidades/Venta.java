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

public class Venta {
    private int idVenta;
    private double iva;
    private Date fechaVenta;
    private double descuento;
    private int idCliente;  // Clave foránea que hace referencia a 'cliente'
    // Constructor
    
    public Venta(int idVenta, double iva, Date fechaVenta, double descuento, int idCliente) {
        this.idVenta = idVenta;
        this.iva = iva;
        this.fechaVenta = fechaVenta;
        this.descuento = descuento;
        this.idCliente = idCliente;
    }

    public Venta() {
    }

    public Venta(double descuento) {
        this.descuento = descuento;
    }
    

    // Getters y setters
    public int getIdVenta() {
        return idVenta;
    }
  
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Venta [idVenta=" + idVenta + ", iva=" + iva  +
                ", fechaVenta=" + fechaVenta + ", descuento=" + descuento + ", idCliente=" + idCliente + "]";
    }
}

