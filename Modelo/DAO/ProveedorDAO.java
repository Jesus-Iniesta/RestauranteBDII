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

public class ProveedorDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/db";
        String username = "usr";
        String password = "psswd";
        return DriverManager.getConnection(url, username, password);
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        String query = "INSERT INTO proveedor (nombre, telefono, direccion) VALUES (?, ?, ?) RETURNING id_proveedor";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            // Aquí deberíamos convertir la dirección a un formato adecuado si es necesario
            stmt.setString(3, proveedor.getDireccion().toString());  // Suponiendo que Dirección se almacena como String

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
            }

            return proveedor;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Proveedor obtenerProveedorPorId(int idProveedor) {
        String query = "SELECT id_proveedor, nombre, telefono, direccion FROM proveedor WHERE id_proveedor = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToProveedor(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Proveedor> obtenerTodosLosProveedores() {
        String query = "SELECT id_proveedor, nombre, telefono, direccion FROM proveedor";
        List<Proveedor> proveedores = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                proveedores.add(mapRowToProveedor(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        String query = "UPDATE proveedor SET nombre = ?, telefono = ?, direccion = ? WHERE id_proveedor = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            // Aquí también debemos convertir la dirección a un formato adecuado
            stmt.setString(3, proveedor.getDireccion().toString());
            stmt.setInt(4, proveedor.getIdProveedor());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarProveedor(int idProveedor) {
        String query = "DELETE FROM proveedor WHERE id_proveedor = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProveedor);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Proveedor
    private Proveedor mapRowToProveedor(ResultSet rs) throws SQLException {
        int idProveedor = rs.getInt("id_proveedor");
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");
        
        
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(","); // Esto depende de cómo almacenes los datos

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());
        
        return new Proveedor(idProveedor, nombre, telefono, direccion);
    }
}
