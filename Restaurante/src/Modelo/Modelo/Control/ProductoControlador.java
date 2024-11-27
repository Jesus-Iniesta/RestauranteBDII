
package Modelo.Control;

/**
 *
 * @author jesus
 */


import Modelo.DAO.ProductosDAO;
import Modelo.Entidades.Categoria;
import Modelo.Entidades.Proveedor;
import Modelo.Entidades.Productos;
import java.sql.Connection;

import java.math.BigDecimal;

public class ProductoControlador {

    private ProductosDAO productosDAO;
    public ProductoControlador(Connection con) {
        this.productosDAO = new ProductosDAO(con);
    }

    public boolean insertarProducto(String nombre, BigDecimal precio, int stock, String descripcion, 
                                    String categoriaNombre, String proveedorNombre) {
        try {
            // Obtener la categoría por su nombre
            Categoria categoria = new Categoria();
            categoria.setCategorias(categoriaNombre);
            categoria = productosDAO.obtenerIdCategoria(categoria);

            if (categoria == null || categoria.getIdCat() == 0) {
                throw new Exception("Categoría no encontrada: " + categoriaNombre);
            }

            // Obtener el proveedor por su nombre
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(proveedorNombre);
            proveedor = productosDAO.obtenerIdProveedor(proveedor);

            if (proveedor == null || proveedor.getIdProveedor() == 0) {
                throw new Exception("Proveedor no encontrado: " + proveedorNombre);
            }

            // Crear el objeto Producto
            Productos producto = new Productos();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setDescripcion(descripcion);
            producto.setIdCat(categoria.getIdCat());
            producto.setIdProveedor(proveedor.getIdProveedor());

            // Insertar el producto en la base de datos
            producto = productosDAO.crearProductos(producto);

            return producto.getIdProductos() != 0; // Retorna true si se insertó correctamente

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre un error
        }
    }
    public boolean actualizarProducto(int id_producto, String nombre, BigDecimal precio, int stock, String descripcion, 
                                    String categoriaNombre, String proveedorNombre){
         try {
            // Obtener la categoría por su nombre
            Categoria categoria = new Categoria();
            categoria.setCategorias(categoriaNombre);
            categoria = productosDAO.obtenerIdCategoria(categoria);

            if (categoria == null || categoria.getIdCat() == 0) {
                throw new Exception("Categoría no encontrada: " + categoriaNombre);
            }

            // Obtener el proveedor por su nombre
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(proveedorNombre);
            proveedor = productosDAO.obtenerIdProveedor(proveedor);

            if (proveedor == null || proveedor.getIdProveedor() == 0) {
                throw new Exception("Proveedor no encontrado: " + proveedorNombre);
            }

            // Crear el objeto Producto
            Productos producto = new Productos();
            producto.setIdProductos(id_producto);
            producto.setIdCat(id_producto);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setDescripcion(descripcion);
            producto.setIdCat(categoria.getIdCat());
            producto.setIdProveedor(proveedor.getIdProveedor());

            // Insertar el producto en la base de datos
            producto = productosDAO.actualizarProductos(producto);

            return producto.getIdProductos() != 0; // Retorna true si se insertó correctamente

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre un error
        }
    }
}

