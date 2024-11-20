/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

/**
 *
 * @author Enrique
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Entidades.*;
import Util.Conexion;

public class ProductoCategoriaDAO {


        private Conexion conexion;
        
    private Connection getConnection() throws SQLException {
        return conexion.obtenerConexionActiva();
    }

    // Insertar un nuevo vínculo entre producto y categoría
    public boolean insertarProductoCategoria(ProductoCategoria productoCategoria) {
        String sql = "INSERT INTO producto_categoria (id_producto, id_cat) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productoCategoria.getIdProducto());
            stmt.setInt(2, productoCategoria.getIdCat());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener un vínculo entre producto y categoría por los dos IDs
    public ProductoCategoria obtenerProductoCategoria(int idProducto, int idCat) {
        String sql = "SELECT * FROM producto_categoria WHERE id_producto = ? AND id_cat = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idCat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new ProductoCategoria(
                        rs.getInt("id_producto"),
                        rs.getInt("id_cat")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los vínculos entre productos y categorías
    public List<ProductoCategoria> obtenerTodasLasProductoCategoria() {
        List<ProductoCategoria> productoCategorias = new ArrayList<>();
        String sql = "SELECT * FROM producto_categoria";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                productoCategorias.add(new ProductoCategoria(
                        rs.getInt("id_producto"),
                        rs.getInt("id_cat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoCategorias;
    }

    // Eliminar un vínculo entre producto y categoría
    public boolean eliminarProductoCategoria(int idProducto, int idCat) {
        String sql = "DELETE FROM producto_categoria WHERE id_producto = ? AND id_cat = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idCat);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

