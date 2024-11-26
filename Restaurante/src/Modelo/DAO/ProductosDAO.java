
package Modelo.DAO;

/**
 *
 * @author Enrique
 */
import java.sql.*;
import java.math.BigDecimal;
import Modelo.Entidades.*;
import javax.swing.table.DefaultTableModel;

public class ProductosDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
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

            // Ejecutar la consulta y obtener el id_producto generado
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
        String query = "SELECT id_producto, nombre, precio, stock,descripcion, id_cat,id_proveedor FROM restaurante.productos WHERE id_producto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

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

    public void obtenerTodosLosProductos(DefaultTableModel modeloTabla) {
         String query = "SELECT id_producto, nombre, precio, stock, id_proveedor FROM restaurante.productos";
        try(
             PreparedStatement stmt = this.conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnas = rsmd.getColumnCount();
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for(int i = 0; i< columnas;i++){
                    fila[i] = rs.getObject(i+1);
                }
                modeloTabla.addRow(fila);
            }
            
        }catch(SQLException e){
            System.out.println("Error al contruir tabla. "+e);
        }
    }

    public Productos actualizarProductos(Productos productos) {
        String query = "UPDATE productos SET nombre = ?, precio = ?, stock = ?,descripcion = ?,id_cat = ?, id_proveedor = ? WHERE id_producto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, productos.getNombre());
            stmt.setBigDecimal(2, productos.getPrecio());
            stmt.setInt(3, productos.getStock());
            stmt.setString(4, productos.getDescripcion());
            stmt.setInt(5, productos.getIdCat());
            stmt.setInt(6, productos.getIdProveedor());
            stmt.setInt(7, productos.getIdProductos());

            return productos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarProductos(int idProductos) {
        String query = "DELETE FROM productoss WHERE id_producto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idProductos);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Productos
    private Productos mapRowToProductos(ResultSet rs) throws SQLException {
        int idProductos = rs.getInt("id_producto");
        String nombre = rs.getString("nombre");
        BigDecimal precio = rs.getBigDecimal("precio");
        int stock = rs.getInt("stock");
        String descripcion = rs.getString("descripcion");
        int idCategoria = rs.getInt("id_cat");
        int idProveedor = rs.getInt("id_proveedor");

        return new Productos(idProductos,nombre,precio,stock,descripcion,idCategoria,idProveedor);
    }
}

