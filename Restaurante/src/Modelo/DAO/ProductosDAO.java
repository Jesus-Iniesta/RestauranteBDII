
package Modelo.DAO;

/**
 *
 * @author Enrique
 */
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import Modelo.Entidades.*;
import Util.Conexion;

public class ProductosDAO {


        private Conexion conexion;
        private Connection conn;
    private Connection getConnection() throws SQLException {
        return conexion.obtenerConexionActiva();
    }

    public ProductosDAO(Connection conn) {
        this.conn = conn;
    }
    

    // Método para obtener el ID de una categoría por su nombre
    public Categoria obtenerIdCategoria(Categoria categoria){
        String query = "SELECT id_cat FROM restaurante.categoria WHERE categorias = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1,categoria.getCategorias() );
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                categoria.setIdCat(rs.getInt("id_cat"));
                
            } 
            return categoria; 
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Método para obtener el ID de un proveedor por su nombre 
    public Proveedor obtenerIdProveedor(Proveedor nombreProveedor){
        String query = "SELECT id_proveedor FROM restaurante.proveedor WHERE nombre = ?";
        try(PreparedStatement stmt = this.conn.prepareStatement(query)){
            stmt.setString(1, nombreProveedor.getNombre());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                nombreProveedor.setIdProveedor(rs.getInt("id_proveedor"));
            }
            return nombreProveedor;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public Productos crearProductos(Productos productos) {
        String query = "INSERT INTO restaurante.productos(nombre, precio, stock,descripcion, id_cat,id_proveedor) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_producto";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer parámetros de la consulta
            stmt.setString(1, productos.getNombre());
            stmt.setBigDecimal(2, productos.getPrecio());
            stmt.setInt(3, productos.getStock());
            stmt.setString(4, productos.getDescripcion());
            stmt.setInt(5, productos.getIdCat());
            stmt.setInt(6, productos.getIdProveedor());

            // Ejecutar la consulta y obtener el id_productos generado
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                productos.setIdProductos(rs.getInt("id_producto"));
            }

            return productos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Productos obtenerProductosPorId(int idProductos) {
        String query = "SELECT id_productos, nombre, precio, stock, id_proveedor FROM productoss WHERE id_productos = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProductos);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToProductos(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Productos> obtenerTodosLosProductoss() {
        String query = "SELECT id_productos, nombre, precio, stock, id_proveedor FROM productos";
        List<Productos> productoss = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productoss.add(mapRowToProductos(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoss;
    }

    public boolean actualizarProductos(Productos productos) {
        String query = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, id_proveedor = ? WHERE id_productos = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productos.getNombre());
            stmt.setBigDecimal(2, productos.getPrecio());
            stmt.setInt(3, productos.getStock());
            stmt.setInt(4, productos.getIdProveedor());
            stmt.setInt(5, productos.getIdProductos());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarProductos(int idProductos) {
        String query = "DELETE FROM productoss WHERE id_productos = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProductos);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Productos
    private Productos mapRowToProductos(ResultSet rs) throws SQLException {
        int idProductos = rs.getInt("id_productos");
        String nombre = rs.getString("nombre");
        BigDecimal precio = rs.getBigDecimal("precio");
        int stock = rs.getInt("stock");
        int idProveedor = rs.getInt("id_proveedor");

        return new Productos(idProductos, nombre, precio, stock, idProveedor);
    }
}

