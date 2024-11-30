
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

public class FacturaDAO {


    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public FacturaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public Factura crearFactura(Factura factura) {
        String query = "INSERT INTO restaurante.factura (rfc_cliente, nombre_cliente, fecha_expedicion,emisor, id_cliente, id_pedido) VALUES (?, ?, CURRENT_DATE, ?, ?,?) RETURNING id_factura";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer parámetros de la consulta
            
            stmt.setString(1, factura.getRfcCliente());
            stmt.setString(2, factura.getNombre_cliente());
            stmt.setString(3, factura.getEmisor());
            stmt.setInt(4, factura.getIdCliente());
            stmt.setInt(5, factura.getIdPedido());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                factura.setIdFactura(rs.getInt("id_factura")); // Establecer el ID generado
            }

            return factura;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
}

    public Factura obtenerFacturaPorId(int id) {
        String query = "SELECT id_factura,rfc_cliente, nombre_cliente, fecha_expedicion,emisor, id_cliente, id_pedido FROM restaurante.factura WHERE id_factura = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            // Establecer parámetros de la consulta
            stmt.setInt(1, id);
            

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
        String query = "SELECT id_factura, rfc_cliente, telefono_cliente, nombre_cliente, fecha_expedicion, id_cliente FROM restaurante.factura";
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
        String query = "UPDATE restaurante.factura " +
                       "SET rfc_cliente = ?, nombre_cliente = ?, fecha_expedicion = ?, emisor = ?, id_cliente = ?, id_pedido = ? " +
                       "WHERE id_factura = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            // Establecer los parámetros de la consulta
            stmt.setString(1, factura.getRfcCliente());
            stmt.setString(2, factura.getNombre_cliente());
            stmt.setDate(3, factura.getFechaExpedicion());
            stmt.setString(4, factura.getEmisor());
            stmt.setInt(5, factura.getIdCliente());
            stmt.setInt(6, factura.getIdPedido());
            stmt.setInt(7, factura.getIdFactura());

            // Ejecutar la actualización
            int rowsUpdated = stmt.executeUpdate();

            // Si se actualizó al menos una fila, el método regresa true
            return rowsUpdated > 0;

            } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Retorna false si ocurre un error o no se actualizan filas
    }

    public boolean eliminarFactura(int idFactura) {
        String query = "DELETE FROM restaurante.factura WHERE id_factura = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idFactura);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void obtenerTodasLasFacturas(DefaultTableModel modeloTabla) {
         String query = "SELECT id_factura,rfc_cliente,emisor,nombre_cliente,fecha_expedicion,id_cliente,id_pedido FROM restaurante.factura ORDER BY id_factura ASC limit 1000";
                                   
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
    public Factura obtenerFacturaPorIdXML(int id) {
    String query = """
        SELECT 
            xmlelement(
                name factura,
                xmlattributes(f.id_factura AS id_factura),
                xmlelement(name rfc_cliente, f.rfc_cliente),
                xmlelement(name nombre_cliente, f.nombre_cliente),
                xmlelement(name fecha_expedicion, f.fecha_expedicion),
                xmlelement(name emisor, f.emisor),
                xmlelement(name id_cliente, f.id_cliente),
                xmlelement(
                    name productos,
                    xmlagg(
                        xmlelement(
                            name producto,
                            xmlelement(name nombre, pr.nombre),
                            xmlelement(name precio, pr.precio),
                            xmlelement(name cantidad, d.cantidad_producto)
                        )
                    )
                ),
                xmlelement(
                    name total_precio_productos,
                    SUM(d.cantidad_producto * pr.precio)
                )
            ) AS factura_xml
        FROM 
            restaurante.factura f
        JOIN 
            restaurante.pedidos p ON f.id_pedido = p.id_pedido
        JOIN (
            SELECT 
                id_pedido,
                unnest(id_producto) AS id_producto,
                unnest(cantidad) AS cantidad_producto
            FROM 
                restaurante.pedidos
        ) d ON p.id_pedido = d.id_pedido
        JOIN 
            restaurante.productos pr ON d.id_producto = pr.id_producto
        WHERE 
            f.id_factura = ?
        GROUP BY 
            f.id_factura, f.rfc_cliente, f.nombre_cliente, f.fecha_expedicion, f.emisor, f.id_cliente;
    """;

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Devuelve el resultado XML como String
                String facturaXml = rs.getString("factura_xml");
                Factura factura = new Factura();
                factura.setFacturaXml(facturaXml);  // Mapea el XML al objeto
                return factura;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
}


    // Mapea un ResultSet a un objeto Factura
    private Factura mapRowToFactura(ResultSet rs) throws SQLException {
    String rfcCliente = rs.getString("rfc_cliente");
    String nombreCliente = rs.getString("nombre_cliente");
    Date fechaExpedicion = rs.getDate("fecha_expedicion");
    String emisor = rs.getString("emisor");
    int idCliente = rs.getInt("id_cliente");
    int idPedido = rs.getInt("id_pedido");

    Factura factura = new Factura(idCliente, rfcCliente, idPedido, nombreCliente, emisor);
    factura.setIdFactura(rs.getInt("id_factura")); // Establecer ID después del constructor

    return factura;
}
}