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
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

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

    public boolean crearVenta(double iva, double descuento, String tipo, String metodoPago, int[] idsProductos, int[] cantidades) {
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

            Map<Integer, Integer> idToCantidadMap = new HashMap<>();
            for (int i = 0; i < idsProductos.length; i++) {
                idToCantidadMap.put(idsProductos[i], cantidades[i]);
            }

            while (rs3.next()) {
                int idProducto = rs3.getInt("id_producto");
                int stock = rs3.getInt("stock");
                int cantidad = idToCantidadMap.getOrDefault(idProducto, 0);

                if (stock < cantidad) {
                    System.err.println("Stock insuficiente para producto con ID " + idProducto);
                    return false; // Insuficiente stock
                }
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

    public void obtenerTodasLasVentas(DefaultTableModel modeloTabla) {
        String query = "SELECT \n" +
            "    v.id_venta AS id_venta,\n" +
            "    v.id_cliente AS id_cliente,\n" +
            "    v.no_cliente_temp AS no_cliente_temp,\n" +
            "    v.fecha_venta AS fecha_venta,\n" +
            "    v.iva AS iva,\n" +
            "    v.descuento AS descuento,\n" +
            "    SUM(d.cantidad_producto * pr.precio) AS subtotal,\n" +
            "    ROUND(SUM(d.cantidad_producto * pr.precio) * (1 + (v.iva / 100)) - v.descuento, 2) AS total\n" +
            "FROM \n" +
            "    restaurante.venta v\n" +
            "JOIN (\n" +
            "    SELECT \n" +
            "        p.id_venta,\n" +
            "        p.fecha_venta,\n" +
            "        unnest(p.id_producto) AS id_producto,\n" +
            "        unnest(p.cantidad) AS cantidad_producto\n" +
            "    FROM \n" +
            "        restaurante.pedidos p\n" +
            ") d \n" +
            "    ON v.id_venta = d.id_venta AND v.fecha_venta = d.fecha_venta\n" +
            "JOIN \n" +
            "    restaurante.productos pr \n" +
            "    ON d.id_producto = pr.id_producto\n" +
            "GROUP BY \n" +
            "    v.id_venta, v.id_cliente, v.no_cliente_temp, v.fecha_venta, v.iva, v.descuento\n" +
            "ORDER BY \n" +
            "    v.id_venta ASC";

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
            System.out.println("Error al construir tabla. "+e);
        }
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
    public void mostrarTablaVenta(DefaultTableModel modeloTabla){
        String query = "SELECT \n" +
            "    v.id_venta AS id_venta,\n" +
            "    v.fecha_venta AS fecha_venta,\n" +
            "    array_agg(pr.nombre) AS productos,\n" +
            "    array_agg(d.cantidad_producto) AS cantidades,\n" +
            "    array_agg(pr.precio) AS precios_unitarios,\n" +
            "    SUM(d.cantidad_producto * pr.precio) AS total_a_pagar\n" +
            "FROM \n" +
            "    restaurante.venta v\n" +
            "JOIN (\n" +
            "    SELECT \n" +
            "        p.id_venta,\n" +
            "        p.fecha_venta,\n" +
            "        unnest(p.id_producto) AS id_producto,\n" +
            "        unnest(p.cantidad) AS cantidad_producto\n" +
            "    FROM \n" +
            "        restaurante.pedidos p\n" +
            ") d \n" +
            "    ON v.id_venta = d.id_venta AND v.fecha_venta = d.fecha_venta\n" +
            "JOIN \n" +
            "    restaurante.productos pr \n" +
            "    ON d.id_producto = pr.id_producto\n" +
            "WHERE \n" +
            "    v.fecha_venta = CURRENT_DATE\n" +
            "GROUP BY \n" +
            "    v.id_venta, v.fecha_venta\n" +
            "ORDER BY \n" +
            "    v.id_venta DESC;";
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
            System.out.println("Error al construir tabla. "+e);
        }
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

