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

    // Constructor
    public Puesto(int idPuesto, String tituloPuesto, String descripcion) {
        this.idPuesto = idPuesto;
        this.tituloPuesto = tituloPuesto;
        this.descripcion = descripcion;
    }

    public Puesto() {
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
    @Override
    public String toString() {
        return "Puesto [idPuesto=" + idPuesto + ", tituloPuesto=" + tituloPuesto + ", descripcion=" + descripcion + "]";
    }
}
