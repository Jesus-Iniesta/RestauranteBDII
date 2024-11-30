
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

public class RestauranteDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public RestauranteDAO(Connection conn) {
        this.conn = conn;
    }

    public Restaurante crearRestaurante(Restaurante restaurante) {
        String query = "INSERT INTO restaurante (nombre, telefono, direccion) " +
                       "VALUES (?, ?, ?::direccion) RETURNING id_restaurante";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, restaurante.getNombre());
            stmt.setString(2, restaurante.getTelefono());

            // Serializar la dirección como un tipo compuesto
            String direccionSQL = restaurante.getDireccion().getCalle() + ", " +
                                  restaurante.getDireccion().getColonia() + ", " +
                                  restaurante.getDireccion().getPais() + ", " +
                                  restaurante.getDireccion().getCp();
            stmt.setString(3, direccionSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                restaurante.setIdRestaurante(rs.getInt("id_restaurante"));
            }
            return restaurante;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Restaurante obtenerRestaurantePorId(int idRestaurante) {
        String query = "SELECT id_restaurante, nombre, telefono, direccion " +
                       "FROM restaurante WHERE id_restaurante = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idRestaurante);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToRestaurante(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String obtenerNombreRestaurantePorNombre(String nombreRestaurante) {
    String obtenerIdQuery = "SELECT id_restaurante FROM restaurante.restaurante WHERE nombre = ?";
    String obtenerNombreQuery = "SELECT nombre FROM restaurante.restaurante WHERE id_restaurante = ?";

    try (
        PreparedStatement obtenerIdStmt = this.conn.prepareStatement(obtenerIdQuery);
        PreparedStatement obtenerNombreStmt = this.conn.prepareStatement(obtenerNombreQuery)
    ) {
        // 1. Buscar el ID del restaurante por su nombre
        obtenerIdStmt.setString(1, nombreRestaurante);
        ResultSet idResult = obtenerIdStmt.executeQuery();

        if (idResult.next()) {
            int idRestaurante = idResult.getInt("id_restaurante");

            // 2. Buscar el nombre del restaurante por su ID
            obtenerNombreStmt.setInt(1, idRestaurante);
            ResultSet nombreResult = obtenerNombreStmt.executeQuery();

            if (nombreResult.next()) {
                return nombreResult.getString("nombre");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null; // Retornar null si no se encontró el restaurante
}


    public List<Restaurante> obtenerTodosLosRestaurantes() {
        String query = "SELECT id_restaurante, nombre, telefono, direccion FROM restaurante";
        List<Restaurante> restaurantes = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                restaurantes.add(mapRowToRestaurante(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantes;
    }

    public boolean actualizarRestaurante(Restaurante restaurante) {
        String query = "UPDATE restaurante SET nombre = ?, telefono = ?, direccion = ?::direccion " +
                       "WHERE id_restaurante = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, restaurante.getNombre());
            stmt.setString(2, restaurante.getTelefono());

            // Serializar la dirección como un tipo compuesto
            String direccionSQL = restaurante.getDireccion().getCalle() + ", " +
                                  restaurante.getDireccion().getColonia() + ", " +
                                  restaurante.getDireccion().getPais() + ", " +
                                  restaurante.getDireccion().getCp();
            stmt.setString(3, direccionSQL);
            stmt.setInt(4, restaurante.getIdRestaurante());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarRestaurante(int idRestaurante) {
        String query = "DELETE FROM restaurante WHERE id_restaurante = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idRestaurante);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Restaurante mapRowToRestaurante(ResultSet rs) throws SQLException {
        int idRestaurante = rs.getInt("id_restaurante");
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");

        // Extraer y mapear la dirección
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());

        return new Restaurante(idRestaurante, nombre, telefono, direccion);
    }
}

