package Modelo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Modelo.Entidades.*;
import Util.Conexion;
import javax.swing.table.DefaultTableModel;

public class ClienteDAO {

    private Connection conn;

    // Constructora que obtiene la conexión a la base de datos
    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }

    // Crear un nuevo cliente
    public Cliente crearCliente(Cliente cliente) {
        // Especificamos el esquema "restaurante"
        String query = "INSERT INTO restaurante.cliente (correo) VALUES (?) RETURNING id_cliente";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
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

    // Obtener un cliente por su ID
    public Cliente obtenerClientePorId(int idCliente) {
        // Especificamos el esquema "restaurante"
        String query = "SELECT p.id_persona, p.nombre, p.apellido_paterno, p.apellido_materno, p.telefono, p.direccion, " +
                       "c.correo " +
                       "FROM restaurante.cliente c " +
                       "JOIN restaurante.persona p ON p.id_persona = c.id_cliente " +
                       "WHERE c.id_cliente = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
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

    // Obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        // Especificamos el esquema "restaurante"
        String query = "SELECT p.id_persona, p.nombre, p.apellido_paterno, p.apellido_materno, p.telefono, p.direccion, " +
                       "c.correo " +
                       "FROM restaurante.cliente c " +
                       "JOIN restaurante.persona p ON p.id_persona = c.id_cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapRowToCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // Actualizar un cliente
    public boolean actualizarCliente(Cliente cliente) {
        // Especificamos el esquema "restaurante"
        String query = "UPDATE restaurante.cliente SET correo = ? WHERE id_cliente = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setString(1, cliente.getCorreo());
            stmt.setInt(2, cliente.getIdPersona());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Eliminar un cliente
    public boolean eliminarCliente(int idCliente) {
        // Especificamos el esquema "restaurante"
        String query = "DELETE FROM restaurante.cliente WHERE id_cliente = ?";

        try (PreparedStatement stmt = this.conn.prepareStatement(query)) {
            stmt.setInt(1, idCliente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para cargar los clientes en la tabla
   public void cargarClientesEnTabla(DefaultTableModel modeloTabla) {
    // Limpiar la tabla antes de cargar los nuevos datos
    modeloTabla.setRowCount(0);

    // Obtener los clientes de la base de datos
    List<Cliente> clientes = obtenerTodosLosClientes();

    // Verificar si la lista de clientes tiene elementos
    if (clientes.isEmpty()) {
        System.out.println("No hay clientes en la base de datos.");
    } else {
        // Agregar los clientes a la tabla
        for (Cliente cliente : clientes) {
            // Concatenar la dirección de los clientes en un solo campo (si es necesario)
            String direccionCompleta = cliente.getDireccion().getCalle() + ", " +
                                       cliente.getDireccion().getColonia() + ", " +
                                       cliente.getDireccion().getPais() + ", " +
                                       cliente.getDireccion().getCp();

            // Agregar la fila con los datos del cliente al modelo de la tabla
            modeloTabla.addRow(new Object[] {
                cliente.getIdPersona(),
                cliente.getNombre(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                direccionCompleta  // Asegúrate de que 'Direccion' tenga un toString adecuado
            });
        }
    }
}

    // Método privado para mapear un ResultSet a un objeto Cliente
    // Método privado para mapear un ResultSet a un objeto Cliente
private Cliente mapRowToCliente(ResultSet rs) throws SQLException {
    int idPersona = rs.getInt("id_persona");
    String nombre = rs.getString("nombre");
    String apellidoPaterno = rs.getString("apellido_paterno");
    String apellidoMaterno = rs.getString("apellido_materno");
    String telefono = rs.getString("telefono");

    // Obtener la dirección como una cadena
    String direccionString = rs.getString("direccion");
    
    // Aquí puedes manejar la dirección de manera que se ajuste a tu formato
    // Si la dirección es una cadena concatenada
    String[] direccionParts = direccionString.split(",");  // Suponiendo que los valores estén separados por coma
    
    // Verificar si la dirección tiene todos los componentes
    String calle = (direccionParts.length > 0) ? direccionParts[0].trim() : "";
    String colonia = (direccionParts.length > 1) ? direccionParts[1].trim() : "";
    String pais = (direccionParts.length > 2) ? direccionParts[2].trim() : "";
    String cp = (direccionParts.length > 3) ? direccionParts[3].trim() : "";

    // Crear la dirección
    Direccion direccion = new Direccion(calle, colonia, pais, cp);

    String correo = rs.getString("correo");

    // Crear y devolver el cliente
    return new Cliente(idPersona, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, correo);
}
}
