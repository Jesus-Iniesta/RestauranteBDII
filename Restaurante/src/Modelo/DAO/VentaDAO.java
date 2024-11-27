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

public class VentaDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public VentaDAO(Connection conn) {
        this.conn = conn;
    }
    // Método auxiliar para convertir int[] a Integer[]
    private Integer[] convertToIntegerArray(int[] array) {
        return java.util.Arrays.stream(array).boxed().toArray(Integer[]::new);
    }

    public boolean actualizarVenta(double iva, double descuento, String tipo, String metodoPago, int[] idsProductos, int[] cantidades) {
        String query1 = "INSERT INTO restaurante.cliente_temp DEFAULT VALUES RETURNING id_cliente_temp";
        String query2 = "INSERT INTO restaurante.venta (iva, fecha_venta, descuento, no_cliente_temp) VALUES (?, CURRENT_DATE, ?, ?) RETURNING id_venta, fecha_venta";
        String query3 = "SELECT id_producto, stock FROM restaurante.productos WHERE id_producto = ANY (?) AND stock >= ANY (?)";
        String query4 = "INSERT INTO restaurante.pedidos (cantidad, tipo, metodo_pago, id_venta, id_producto, fecha_venta) VALUES (?, ?, ?, ?, ?, CURRENT_DATE) RETURNING id_pedido";
        String query5 = "UPDATE restaurante.productos SET stock = stock - cantidades.cantidad FROM (SELECT unnest(?) AS id_producto, unnest(?) AS cantidad) AS cantidades WHERE restaurante.productos.id_producto = cantidades.id_producto";

        try (PreparedStatement stmt1 = this.conn.prepareStatement(query1);
             PreparedStatement stmt2 = this.conn.prepareStatement(query2);
             PreparedStatement stmt3 = this.conn.prepareStatement(query3);
             PreparedStatement stmt4 = this.conn.prepareStatement(query4);
             PreparedStatement stmt5 = this.conn.prepareStatement(query5)) {

            // Paso 1: Insertar en cliente_temp
            ResultSet rs1 = stmt1.executeQuery();
            if (!rs1.next()) return false;
            int idClienteTemp = rs1.getInt("id_cliente_temp");

            // Paso 2: Insertar en venta
            stmt2.setDouble(1, iva);
            stmt2.setDouble(2, descuento);
            stmt2.setInt(3, idClienteTemp);
            ResultSet rs2 = stmt2.executeQuery();
            if (!rs2.next()) return false;
            int idVenta = rs2.getInt("id_venta");

            // Convertir int[] a Integer[]
            Integer[] idsProductosWrapper = convertToIntegerArray(idsProductos);
            Integer[] cantidadesWrapper = convertToIntegerArray(cantidades);

            // Paso 3: Validar stock
            stmt3.setArray(1, this.conn.createArrayOf("INTEGER", idsProductosWrapper));
            stmt3.setArray(2, this.conn.createArrayOf("INTEGER", cantidadesWrapper));
            ResultSet rs3 = stmt3.executeQuery();
            while (rs3.next()) {
                int stock = rs3.getInt("stock");
                if (stock < rs3.getInt("cantidad")) return false; // Insuficiente stock
            }

            // Paso 4: Insertar en pedidos
            stmt4.setArray(1, this.conn.createArrayOf("INTEGER", cantidadesWrapper));
            stmt4.setString(2, tipo);
            stmt4.setString(3, metodoPago);
            stmt4.setInt(4, idVenta);
            stmt4.setArray(5, this.conn.createArrayOf("INTEGER", idsProductosWrapper));
            ResultSet rs4 = stmt4.executeQuery();
            if (!rs4.next()) return false;

            // Paso 5: Actualizar stock
            stmt5.setArray(1, this.conn.createArrayOf("INTEGER", idsProductosWrapper));
            stmt5.setArray(2, this.conn.createArrayOf("INTEGER", cantidadesWrapper));
            return stmt5.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        
        Date fechaVenta = rs.getDate("fecha_venta");
        double descuento = rs.getDouble("descuento");
        int idCliente = rs.getInt("id_cliente");

        return new Venta(idVenta, iva,  fechaVenta, descuento, idCliente);
    }
}

