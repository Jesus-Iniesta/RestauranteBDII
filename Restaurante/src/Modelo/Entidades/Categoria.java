/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class Categoria {
    private int idCat;
    private String categorias;
    private String descripcion;

    public Categoria(int idCat, String categorias, String descripcion) {
        this.idCat = idCat;
        this.categorias = categorias;
        this.descripcion = descripcion;
    }

    public Categoria() {
    }
    // Getters y setters
    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getCategorias() {
        return categorias;
    }

    public void setCategorias(String categorias) {
        this.categorias = categorias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categoria{idCat=" + idCat + ", categorias='" + categorias + "', descripcion='" + descripcion + "'}";
    }
}
