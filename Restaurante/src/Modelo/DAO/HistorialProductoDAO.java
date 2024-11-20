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
import java.math.BigDecimal;
import java.time.LocalDate;
import Modelo.Entidades.*;
import Util.Conexion;

public class HistorialProductoDAO {


        private Conexion conexion;
        
    private Connection getConnection() throws SQLException {
        return conexion.obtenerConexionActiva();
    }

    // Insertar un nuevo historial de producto
    public boolean insertarHistorialProducto(HistorialProducto historialProducto) {
        String sql = "INSERT INTO historial_producto (id_producto, nombre_producto, fecha_compra, precio) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, historialProducto.getIdProducto());
            stmt.setString(2, historialProducto.getNombreProducto());
            stmt.setDate(3, Date.valueOf(historialProducto.getFechaCompra()));
            stmt.setBigDecimal(4, historialProducto.getPrecio());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener un historial de producto por id_producto
    public HistorialProducto obtenerHistorialPorIdProducto(int idProducto) {
        String sql = "SELECT * FROM historial_producto WHERE id_producto = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HistorialProducto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getDate("fecha_compra").toLocalDate(),
                        rs.getBigDecimal("precio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los historiales de productos
    public List<HistorialProducto> obtenerTodosLosHistoriales() {
        List<HistorialProducto> historialProductos = new ArrayList<>();
        String sql = "SELECT * FROM historial_producto";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                historialProductos.add(new HistorialProducto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getDate("fecha_compra").toLocalDate(),
                        rs.getBigDecimal("precio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historialProductos;
    }

    // Eliminar un historial de producto
    public boolean eliminarHistorialProducto(int idProducto) {
        String sql = "DELETE FROM historial_producto WHERE id_producto = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

