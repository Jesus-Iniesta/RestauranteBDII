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

public class FacturaDAO {


    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Factura crearFactura(Factura factura) {
        String query = "INSERT INTO factura (rfc_cliente, telefono_cliente, nombre_cliente, fecha_expedicion, id_cliente) VALUES (?, ?, ?, ?, ?) RETURNING id_factura";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer parámetros de la consulta
            stmt.setString(1, factura.getRfcCliente());
            stmt.setString(2, factura.getTelefonoCliente());
            stmt.setString(3, factura.getNombreCliente());
            stmt.setDate(4, factura.getFechaExpedicion());
            stmt.setInt(5, factura.getIdCliente());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                factura.setIdFactura(rs.getInt("id_factura"));
            }

            return factura;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Factura obtenerFacturaPorId(int idFactura) {
        String query = "SELECT id_factura, rfc_cliente, telefono_cliente, nombre_cliente, fecha_expedicion, id_cliente FROM factura WHERE id_factura = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idFactura);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToFactura(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Factura> obtenerTodasLasFacturas() {
        String query = "SELECT id_factura, rfc_cliente, telefono_cliente, nombre_cliente, fecha_expedicion, id_cliente FROM factura";
        List<Factura> facturas = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                facturas.add(mapRowToFactura(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    public boolean actualizarFactura(Factura factura) {
        String query = "UPDATE factura SET rfc_cliente = ?, telefono_cliente = ?, nombre_cliente = ?, fecha_expedicion = ?, id_cliente = ? WHERE id_factura = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, factura.getRfcCliente());
            stmt.setString(2, factura.getTelefonoCliente());
            stmt.setString(3, factura.getNombreCliente());
            stmt.setDate(4, factura.getFechaExpedicion());
            stmt.setInt(5, factura.getIdCliente());
            stmt.setInt(6, factura.getIdFactura());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarFactura(int idFactura) {
        String query = "DELETE FROM factura WHERE id_factura = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Factura
    private Factura mapRowToFactura(ResultSet rs) throws SQLException {
        int idFactura = rs.getInt("id_factura");
        String rfcCliente = rs.getString("rfc_cliente");
        String telefonoCliente = rs.getString("telefono_cliente");
        String nombreCliente = rs.getString("nombre_cliente");
        Date fechaExpedicion = rs.getDate("fecha_expedicion");
        int idCliente = rs.getInt("id_cliente");

        return new Factura(idFactura, rfcCliente, telefonoCliente, nombreCliente, fechaExpedicion, idCliente);
    }
}

