package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.math.BigDecimal;

public class Productos {
    private int idProductos;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private int idProveedor;  // Clave foránea que hace referencia a 'proveedor'
    private int idCat; //Clave foránea que hace referencia a 'Categoria'

    // Constructor
    public Productos(int idProductos, String nombre, BigDecimal precio, int stock, int idProveedor) {
        this.idProductos = idProductos;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    public Productos(String nombre) {
        this.nombre = nombre;
    }

    public Productos() {
    }
    

    // Getters y setters
    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    @Override
    public String toString() {
        return "Productos [idProductos=" + idProductos + ", nombre=" + nombre + ", precio=" + precio + 
                ", stock=" + stock + ", idProveedor=" + idProveedor + "]";
    }
}

