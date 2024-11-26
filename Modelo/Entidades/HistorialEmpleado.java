/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */import java.time.LocalDate;

public class HistorialEmpleado {
    private int idEmpleado;
    private LocalDate fechaInicioPuesto;
    private LocalDate fechaFinPuesto;
    private int idPuesto;

    // Constructor
    public HistorialEmpleado(int idEmpleado, LocalDate fechaInicioPuesto, LocalDate fechaFinPuesto, int idPuesto) {
        this.idEmpleado = idEmpleado;
        this.fechaInicioPuesto = fechaInicioPuesto;
        this.fechaFinPuesto = fechaFinPuesto;
        this.idPuesto = idPuesto;
    }

    // Getters y setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFechaInicioPuesto() {
        return fechaInicioPuesto;
    }

    public void setFechaInicioPuesto(LocalDate fechaInicioPuesto) {
        this.fechaInicioPuesto = fechaInicioPuesto;
    }

    public LocalDate getFechaFinPuesto() {
        return fechaFinPuesto;
    }

    public void setFechaFinPuesto(LocalDate fechaFinPuesto) {
        this.fechaFinPuesto = fechaFinPuesto;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    @Override
    public String toString() {
        return "HistorialEmpleado{" +
                "idEmpleado=" + idEmpleado +
                ", fechaInicioPuesto=" + fechaInicioPuesto +
                ", fechaFinPuesto=" + fechaFinPuesto +
                ", idPuesto=" + idPuesto +
                '}';
    }
}

