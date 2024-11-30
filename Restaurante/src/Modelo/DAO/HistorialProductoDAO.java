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
import javax.swing.table.DefaultTableModel;

public class HistorialProductoDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public HistorialProductoDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar un nuevo historial de producto
    public boolean insertarHistorialProducto(HistorialProducto historialProducto) {
        String sql = "INSERT INTO historial_producto (id_producto, nombre_producto, fecha_compra, precio) VALUES (?, ?, ?, ?)";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
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
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
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
    public void obtenerTodosLosHistoriales(DefaultTableModel modeloTabla) {
        String query = "SELECT * FROM restaurante.historial_producto";
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

    // Eliminar un historial de producto
    public boolean eliminarHistorialProducto(int idProducto) {
        String sql = "DELETE FROM historial_producto WHERE id_producto = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

