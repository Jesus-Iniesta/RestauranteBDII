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
import java.math.BigDecimal;

public class PedidoDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public PedidoDAO(Connection conn) {
        this.conn = conn;
    }
    public double calcularPrecioTotalDescuento(String nombreProducto, int cantidad,double descuento) {
        String query = "SELECT precio FROM restaurante.productos WHERE nombre = ?";
        double precioUnitario = 0.0;

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                precioUnitario = rs.getDouble("precio");
            } else {
                System.out.println("Producto no encontrado.");
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        // Calcular el precio total
        double subtotal = precioUnitario * cantidad;
        return subtotal - (precioUnitario*descuento);
    }
    public double calcularPrecioTotal(String nombreProducto, int cantidad) {
        String query = "SELECT precio FROM restaurante.productos WHERE nombre = ?";
        double precioUnitario = 0.0;

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1, nombreProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                precioUnitario = rs.getDouble("precio");
            } else {
                System.out.println("Producto no encontrado.");
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        // Calcular el precio total
        double subtotal = precioUnitario * cantidad;
        return subtotal;
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
