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

public class PedidoDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Pedido crearPedido(Pedido pedido) {
        String query = "INSERT INTO pedidos (precio, cantidad, tipo, productos, metodo_pago, id_venta, id_producto, fecha_venta) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id_pedido";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer los parámetros de la consulta
            //stmt.setDouble(1, pedido.getPrecio());
            //stmt.setInt(2, pedido.getCantidad());
            stmt.setString(3, pedido.getTipo());
            //stmt.setString(4, pedido.getProductos());
            stmt.setString(5, pedido.getMetodoPago());
            stmt.setInt(6, pedido.getIdVenta());
            //stmt.setInt(7, pedido.getIdProducto());
            stmt.setDate(8, pedido.getFechaVenta());

            // Ejecutar la consulta y obtener el id_pedido generado
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pedido.setIdPedido(rs.getInt("id_pedido"));
            }

            return pedido;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pedido obtenerPedidoPorId(int idPedido) {
        String query = "SELECT id_pedido, precio, cantidad, tipo, productos, metodo_pago, id_venta, id_producto, fecha_venta " +
                       "FROM pedidos WHERE id_pedido = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                //return mapRowToPedido(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean actualizarPedido(Pedido pedido) {
        String query = "UPDATE pedidos SET precio = ?, cantidad = ?, tipo = ?, productos = ?, metodo_pago = ?, " +
                       "id_venta = ?, id_producto = ?, fecha_venta = ? WHERE id_pedido = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            //stmt.setDouble(1, pedido.getPrecio());
           // stmt.setInt(2, pedido.getCantidad());
            stmt.setString(3, pedido.getTipo());
            //stmt.setString(4, pedido.getProductos());
            stmt.setString(5, pedido.getMetodoPago());
            stmt.setInt(6, pedido.getIdVenta());
           // stmt.setInt(7, pedido.getIdProducto());
            stmt.setDate(8, pedido.getFechaVenta());
            stmt.setInt(9, pedido.getIdPedido());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarPedido(int idPedido) {
        String query = "DELETE FROM pedidos WHERE id_pedido = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPedido);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
