
package Modelo.Control;

/**
 *
 * @author Jesus
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarritoDeCompras {

    private ArrayList<Integer> idsProductos;   // Lista de IDs de productos
    private ArrayList<Integer> cantidades;     // Lista de cantidades
    private double TotalVenta;
    private double descuentoTotal;
    private double subTotal;

    public CarritoDeCompras() {
        idsProductos = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public CarritoDeCompras(double TotalVenta) {
        this.TotalVenta = TotalVenta;
    
    }

    
    // Método para agregar un producto al carrito
    public void agregarProducto(int idProducto, int cantidad) {
        idsProductos.add(idProducto);
        cantidades.add(cantidad);
        System.out.println("Producto agregado: ID = " + idProducto + ", Cantidad = " + cantidad);
    }
    //Método para calcular el total 
    

    // Método para mostrar el contenido actual del carrito
    public void mostrarCarrito() {
        System.out.println("Carrito de Compras:");
        for (int i = 0; i < idsProductos.size(); i++) {
            System.out.println("ID Producto: " + idsProductos.get(i) + ", Cantidad: " + cantidades.get(i));
        }
    }

    // Método para limpiar el carrito
    public void limpiarCarrito() {
        idsProductos.clear();
        cantidades.clear();
        TotalVenta = 0.0;
        descuentoTotal = 0.0;
        subTotal = 0.0;
        System.out.println("Carrito limpiado.");
    }
    public double calcularTotal(double subtotal){
        TotalVenta += subtotal;    
        return TotalVenta;
    }
    public double calcularTotalSinDesc(double subtotalsinDesc){
        subTotal += subtotalsinDesc;
        return subTotal;
    }
    public double descuentoTotal(double subtotal,double total){
        descuentoTotal = (subtotal-total)/subtotal;
        return descuentoTotal;
    }

    // **Nuevo**: Obtener la lista de IDs de productos
    public List<Integer> getIdsProductos() {
        return idsProductos;
    }

    // **Nuevo**: Obtener la lista de cantidades
    public List<Integer> getCantidades() {
        return cantidades;
    }

    public void setTotalVenta(double TotalVenta) {
        this.TotalVenta = TotalVenta;
    }

    public double getTotalVenta() {
        return TotalVenta;
    }

    public double getDescuentoTotal() {
        return descuentoTotal;
    }

    public void setDescuentoTotal(double descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    
}

