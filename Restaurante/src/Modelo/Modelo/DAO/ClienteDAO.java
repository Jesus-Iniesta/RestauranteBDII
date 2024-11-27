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

public class ClienteDAO {


    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }

    public Cliente crearCliente(Cliente cliente) {
        String query = "INSERT INTO cliente (correo) VALUES (?) RETURNING id_cliente";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getCorreo());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setIdPersona(rs.getInt("id_cliente"));
            }
            return cliente;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente obtenerClientePorId(int idCliente) {
        String query = "SELECT p.id_persona, p.nombre, p.apellido_paterno, p.apellido_materno, p.telefono, p.direccion, " +
                       "c.correo " +
                       "FROM cliente c " +
                       "JOIN persona p ON p.id_persona = c.id_cliente " +
                       "WHERE c.id_cliente = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToCliente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        String query = "SELECT p.id_persona, p.nombre, p.apellido_paterno, p.apellido_materno, p.telefono, p.direccion, " +
                       "c.correo " +
                       "FROM cliente c " +
                       "JOIN persona p ON p.id_persona = c.id_cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapRowToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public boolean actualizarCliente(Cliente cliente) {
        String query = "UPDATE cliente SET correo = ? WHERE id_cliente = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, cliente.getCorreo());
            stmt.setInt(2, cliente.getIdPersona());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarCliente(int idCliente) {
        String query = "DELETE FROM cliente WHERE id_cliente = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idCliente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Cliente mapRowToCliente(ResultSet rs) throws SQLException {
        int idPersona = rs.getInt("id_persona");
        String nombre = rs.getString("nombre");
        String apellidoPaterno = rs.getString("apellido_paterno");
        String apellidoMaterno = rs.getString("apellido_materno");
        String telefono = rs.getString("telefono");
        
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());
        
        String correo = rs.getString("correo");

        return new Cliente(idPersona, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, correo);
    }
}

