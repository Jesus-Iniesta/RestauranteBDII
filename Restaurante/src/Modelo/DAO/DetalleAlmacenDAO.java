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

public class DetalleAlmacenDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/db";
        String username = "usr";
        String password = "psswd";
        return DriverManager.getConnection(url, username, password);
    }

    public DetalleAlmacen crearDetalleAlmacen(DetalleAlmacen detalleAlmacen) {
        String query = "INSERT INTO detalle_almacen (id_almacen, id_producto, cantidad_utilizada) VALUES (?, ?, ?) RETURNING id_detalle";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            stmt.setInt(1, detalleAlmacen.getIdAlmacen());
            stmt.setInt(2, detalleAlmacen.getIdProducto());
            stmt.setInt(3, detalleAlmacen.getCantidadUtilizada());

            // Ejecutar la consulta y obtener el id_detalle generado
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                detalleAlmacen.setIdDetalle(rs.getInt("id_detalle"));
            }

            return detalleAlmacen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public DetalleAlmacen obtenerDetalleAlmacenPorId(int idDetalle) {
        String query = "SELECT id_detalle, id_almacen, id_producto, cantidad_utilizada FROM detalle_almacen WHERE id_detalle = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDetalle);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToDetalleAlmacen(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DetalleAlmacen> obtenerDetallesPorAlmacen(int idAlmacen) {
        String query = "SELECT id_detalle, id_almacen, id_producto, cantidad_utilizada FROM detalle_almacen WHERE id_almacen = ?";
        List<DetalleAlmacen> detalles = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAlmacen);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                detalles.add(mapRowToDetalleAlmacen(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    public boolean actualizarDetalleAlmacen(DetalleAlmacen detalleAlmacen) {
        String query = "UPDATE detalle_almacen SET id_almacen = ?, id_producto = ?, cantidad_utilizada = ? WHERE id_detalle = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, detalleAlmacen.getIdAlmacen());
            stmt.setInt(2, detalleAlmacen.getIdProducto());
            stmt.setInt(3, detalleAlmacen.getCantidadUtilizada());
            stmt.setInt(4, detalleAlmacen.getIdDetalle());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarDetalleAlmacen(int idDetalle) {
        String query = "DELETE FROM detalle_almacen WHERE id_detalle = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDetalle);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto DetalleAlmacen
    private DetalleAlmacen mapRowToDetalleAlmacen(ResultSet rs) throws SQLException {
        int idDetalle = rs.getInt("id_detalle");
        int idAlmacen = rs.getInt("id_almacen");
        int idProducto = rs.getInt("id_producto");
        int cantidadUtilizada = rs.getInt("cantidad_utilizada");

        return new DetalleAlmacen(idDetalle, idAlmacen, idProducto, cantidadUtilizada);
    }
}

