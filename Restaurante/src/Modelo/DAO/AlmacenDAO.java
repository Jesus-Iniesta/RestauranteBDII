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

public class AlmacenDAO {

        private Conexion conexion;
        
    private Connection getConnection() throws SQLException {
        return conexion.obtenerConexionActiva();
    }

    public Almacen crearAlmacen(Almacen almacen) {
        String query = "INSERT INTO almacen (nombre, fecha_caducidad, stock) VALUES (?, ?, ?) RETURNING id_almacen";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            stmt.setString(1, almacen.getNombre());
            stmt.setDate(2, almacen.getFechaCaducidad());
            stmt.setInt(3, almacen.getStock());

            // Ejecutar la consulta y obtener el id_almacen generado
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                almacen.setIdAlmacen(rs.getInt("id_almacen"));
            }

            return almacen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Almacen obtenerAlmacenPorId(int idAlmacen) {
        String query = "SELECT id_almacen, nombre, fecha_caducidad, stock FROM almacen WHERE id_almacen = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAlmacen);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToAlmacen(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Almacen> obtenerTodosLosAlmacenes() {
        String query = "SELECT id_almacen, nombre, fecha_caducidad, stock FROM almacen";
        List<Almacen> almacenes = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                almacenes.add(mapRowToAlmacen(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almacenes;
    }

    public boolean actualizarAlmacen(Almacen almacen) {
        String query = "UPDATE almacen SET nombre = ?, fecha_caducidad = ?, stock = ? WHERE id_almacen = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, almacen.getNombre());
            stmt.setDate(2, almacen.getFechaCaducidad());
            stmt.setInt(3, almacen.getStock());
            stmt.setInt(4, almacen.getIdAlmacen());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarAlmacen(int idAlmacen) {
        String query = "DELETE FROM almacen WHERE id_almacen = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAlmacen);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Almacen
    private Almacen mapRowToAlmacen(ResultSet rs) throws SQLException {
        int idAlmacen = rs.getInt("id_almacen");
        String nombre = rs.getString("nombre");
        Date fechaCaducidad = rs.getDate("fecha_caducidad");
        int stock = rs.getInt("stock");

        return new Almacen(idAlmacen, nombre, fechaCaducidad, stock);
    }
}

