/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class Puesto {
    private int idPuesto;
    private String tituloPuesto;
    private String descripcion;
    private int idEmpleado;  // Relación con la tabla 'empleado'

    // Constructor
    public Puesto(int idPuesto, String tituloPuesto, String descripcion, int idEmpleado) {
        this.idPuesto = idPuesto;
        this.tituloPuesto = tituloPuesto;
        this.descripcion = descripcion;
        this.idEmpleado = idEmpleado;
    }

    // Getters y setters
    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getTituloPuesto() {
        return tituloPuesto;
    }

    public void setTituloPuesto(String tituloPuesto) {
        this.tituloPuesto = tituloPuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public String toString() {
        return "Puesto [idPuesto=" + idPuesto + ", tituloPuesto=" + tituloPuesto + ", descripcion=" + descripcion + "]";
    }
}
