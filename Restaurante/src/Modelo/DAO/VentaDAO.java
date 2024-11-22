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

public class VentaDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public VentaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Venta crearVenta(Venta venta) {
        String query = "INSERT INTO venta (IVA, cliente, fecha_venta, descuento, id_cliente) VALUES (?, ?, ?, ?, ?) RETURNING id_venta";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer parámetros de la consulta
            stmt.setDouble(1, venta.getIva());
            stmt.setString(2, venta.getCliente());
            stmt.setDate(3, venta.getFechaVenta());
            stmt.setDouble(4, venta.getDescuento());
            stmt.setInt(5, venta.getIdCliente());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                venta.setIdVenta(rs.getInt("id_venta"));
            }

            return venta;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Venta obtenerVentaPorId(int idVenta, Date fechaVenta) {
        String query = "SELECT id_venta, IVA, cliente, fecha_venta, descuento, id_cliente FROM venta WHERE id_venta = ? AND fecha_venta = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idVenta);
            stmt.setDate(2, fechaVenta);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToVenta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Venta> obtenerTodasLasVentas() {
        String query = "SELECT id_venta, IVA, cliente, fecha_venta, descuento, id_cliente FROM venta";
        List<Venta> ventas = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ventas.add(mapRowToVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    public boolean actualizarVenta(Venta venta) {
        String query = "UPDATE venta SET IVA = ?, cliente = ?, fecha_venta = ?, descuento = ?, id_cliente = ? WHERE id_venta = ? AND fecha_venta = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setDouble(1, venta.getIva());
            stmt.setString(2, venta.getCliente());
            stmt.setDate(3, venta.getFechaVenta());
            stmt.setDouble(4, venta.getDescuento());
            stmt.setInt(5, venta.getIdCliente());
            stmt.setInt(6, venta.getIdVenta());
            stmt.setDate(7, venta.getFechaVenta());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarVenta(int idVenta, Date fechaVenta) {
        String query = "DELETE FROM venta WHERE id_venta = ? AND fecha_venta = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idVenta);
            stmt.setDate(2, fechaVenta);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Venta
    private Venta mapRowToVenta(ResultSet rs) throws SQLException {
        int idVenta = rs.getInt("id_venta");
        double iva = rs.getDouble("IVA");
        String cliente = rs.getString("cliente");
        Date fechaVenta = rs.getDate("fecha_venta");
        double descuento = rs.getDouble("descuento");
        int idCliente = rs.getInt("id_cliente");

        return new Venta(idVenta, iva, cliente, fechaVenta, descuento, idCliente);
    }
}

