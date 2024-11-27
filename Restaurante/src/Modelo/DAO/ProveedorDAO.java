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
    private Direccion direccion;

    //Constructora que obtiene la conexion a la base de datos
    public ProveedorDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean crearProveedor(Proveedor proveedor) {
        String query = "INSERT INTO restaurante.proveedor (nombre, telefono, direccion) VALUES (?, ?, ROW(?,?,?,?)) RETURNING id_proveedor";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            this.direccion = direccion;
            
            // Establecer los parámetros de la consulta
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getDireccion().getCalle());  
            stmt.setString(4, proveedor.getDireccion().getColonia());
            stmt.setString(5, proveedor.getDireccion().getPais());
            stmt.setString(6, proveedor.getDireccion().getCp());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Proveedor obtenerProveedorPorId(int idProveedor) {
        String query = "SELECT nombre,direccion,telefono FROM restaurante.proveedor WHERE id_proveedor = ?";
        Proveedor proveedor = new Proveedor();
            
        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(rs.getString("direccion"));
                proveedor.setTelefono(rs.getString("telefono"));
            }
            return proveedor;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    public Proveedor obtenerProveedorIdPorNombre(String nombreProveedor) {
        String query = "SELECT id_proveedor FROM restaurante.proveedor WHERE nombre = ?";
        Proveedor proveedor = new Proveedor();
            
        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, nombreProveedor);
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

    public boolean actualizarProveedor(Proveedor proveedor,int id_proveedor) {
        String query = "UPDATE restaurante.proveedor SET nombre = ?, telefono = ?, direccion = ROW(?, ?, ?, ?) WHERE id_proveedor = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Asignar valores a los placeholders (?)
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());

            // Descomponer la dirección y asignar sus atributos
            Direccion direccion = proveedor.getDireccion();
            stmt.setString(3, direccion.getCalle());
            stmt.setString(4, direccion.getColonia());
            stmt.setString(5, direccion.getPais());
            stmt.setString(6, direccion.getCp());
            // Asignar el ID del proveedor
            stmt.setInt(7, id_proveedor); // Este es el último placeholder de la consulta
            // Ejecutar el UPDATE y retornar si se afectó al menos una fila
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

    public boolean actualizarProveedor(int idProveedor, String nombre, String telefono, String calle, String colonia, String pais, String cp) {
        
        String query = "UPDATE proveedor SET nombre = ?, telefono = ?, direccion = ? WHERE id_proveedor = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombre);
            proveedor.setTelefono(telefono);
            
            // Establecer los parámetros de la consulta
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            
            this.direccion = new Direccion(calle,colonia,pais,cp);
            proveedor.setDireccion(this.direccion);
            
            String direccionSQL = proveedor.getDireccion().getCalle() + ", " +
                                  proveedor.getDireccion().getColonia() + ", " +
                                  proveedor.getDireccion().getPais() + ", " +
                                  proveedor.getDireccion().getCp();
            stmt.setString(3, direccionSQL); 
            
            proveedor.setIdProveedor(idProveedor);
            
            stmt.setInt(4, proveedor.getIdProveedor());
            

            ResultSet rs = stmt.executeQuery();

            return true;

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
         String query = "SELECT id_proveedor, nombre,direccion,telefono FROM restaurante.proveedor ORDER BY id_proveedor ASC";
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

