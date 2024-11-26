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
import javax.swing.table.DefaultTableModel;

public class ProveedorDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public ProveedorDAO(Connection conn) {
        this.conn = conn;
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        String query = "INSERT INTO proveedor (nombre, telefono, direccion) VALUES (?, ?, ?) RETURNING id_proveedor";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getDireccion().toString());  

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

    public String obtenerProveedorPorId(int idProveedor, Proveedor proveedor) {
        String query = "SELECT nombre FROM restaurante.proveedor WHERE id_proveedor = ?";
        
        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                proveedor.setNombre(rs.getString("nombre"));
            }
            return proveedor.getNombre();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Proveedor> obtenerTodosLosProveedores() {
        String query = "SELECT id_proveedor, nombre, telefono, direccion FROM proveedor";
        List<Proveedor> proveedores = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
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

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
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

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idProveedor);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void obtenerTodosLosProveedor(DefaultTableModel modeloTabla) {
         String query = "SELECT id_proveedor, nombre,direccion,telefono FROM restaurante.proveedor";
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

    // Mapea un ResultSet a un objeto Proveedor
    private Proveedor mapRowToProveedor(ResultSet rs) throws SQLException {
        int idProveedor = rs.getInt("id_proveedor");
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());
        
        return new Proveedor(idProveedor, nombre, telefono, direccion);
    }
}

