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

public class PuestoDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public PuestoDAO(Connection conn) {
        this.conn = conn;
    }

    public Puesto crearPuesto(Puesto puesto) {
        String query = "INSERT INTO puesto (titulo_puesto, descripcion) VALUES (?, ?, ?) RETURNING id_puesto";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, puesto.getTituloPuesto());
            stmt.setString(2, puesto.getDescripcion());
            

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                puesto.setIdPuesto(rs.getInt("id_puesto"));
            }

            return puesto;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Puesto obtenerPuestoPorId(int idPuesto) {
        String query = "SELECT id_puesto, titulo_puesto, descripcion, id_empleado FROM puesto WHERE id_puesto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPuesto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToPuesto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Puesto> obtenerTodosLosPuestos() {
        String query = "SELECT id_puesto, titulo_puesto, descripcion, id_empleado FROM puesto";
        List<Puesto> puestos = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                puestos.add(mapRowToPuesto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return puestos;
    }

    public boolean actualizarPuesto(Puesto puesto) {
        String query = "UPDATE puesto SET titulo_puesto = ?, descripcion = ? WHERE id_puesto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, puesto.getTituloPuesto());
            stmt.setString(2, puesto.getDescripcion());
            stmt.setInt(3, puesto.getIdPuesto());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarPuesto(int idPuesto) {
        String query = "DELETE FROM puesto WHERE id_puesto = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPuesto);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Mapea un ResultSet a un objeto Puesto
    private Puesto mapRowToPuesto(ResultSet rs) throws SQLException {
        int idPuesto = rs.getInt("id_puesto");
        String tituloPuesto = rs.getString("titulo_puesto");
        String descripcion = rs.getString("descripcion");

        return new Puesto(idPuesto, tituloPuesto, descripcion);
    }
}

