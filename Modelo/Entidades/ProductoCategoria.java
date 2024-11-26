/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class ProductoCategoria {
    private int idProducto;
    private int idCat;

    public ProductoCategoria(int idProducto, int idCat) {
        this.idProducto = idProducto;
        this.idCat = idCat;
    }

    // Getters y setters
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    @Override
    public String toString() {
        return "ProductoCategoria{idProducto=" + idProducto + ", idCat=" + idCat + "}";
    }
}

