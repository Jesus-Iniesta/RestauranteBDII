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

public class PersonaDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public PersonaDAO(Connection conn) {
        this.conn = conn;
    }


    public Persona crearPersona(Persona persona) {
        String query = "INSERT INTO persona (nombre, apellido_paterno, apellido_materno, telefono, direccion) " +
                       "VALUES (?, ?, ?, ?, ?::direccion) RETURNING id_persona";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellidoPaterno());
            stmt.setString(3, persona.getApellidoMaterno());
            stmt.setString(4, persona.getTelefono());

            // Serializa la dirección como un tipo compuesto
            String direccionSQL = persona.getDireccion().getCalle() + ", " +
                                  persona.getDireccion().getColonia() + ", " +
                                  persona.getDireccion().getPais() + ", " +
                                  persona.getDireccion().getCp();
            stmt.setString(5, direccionSQL); 

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                persona.setIdPersona(rs.getInt("id_persona"));
            }
            return persona;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Persona obtenerPersonaPorId(int idPersona) {
        String query = "SELECT id_persona, nombre, apellido_paterno, apellido_materno, telefono, direccion " +
                       "FROM persona WHERE id_persona = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPersona);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToPersona(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Persona> obtenerTodasLasPersonas() {
        String query = "SELECT id_persona, nombre, apellido_paterno, apellido_materno, telefono, direccion FROM persona";
        List<Persona> personas = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                personas.add(mapRowToPersona(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personas;
    }

    public boolean actualizarPersona(Persona persona) {
        String query = "UPDATE persona SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, telefono = ?, direccion = ?::direccion " +
                       "WHERE id_persona = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, persona.getNombre());
            stmt.setString(2, persona.getApellidoPaterno());
            stmt.setString(3, persona.getApellidoMaterno());
            stmt.setString(4, persona.getTelefono());

            // Serializar la dirección como tipo compuesto
            String direccionSQL = persona.getDireccion().getCalle() + ", " +
                                  persona.getDireccion().getColonia() + ", " +
                                  persona.getDireccion().getPais() + ", " +
                                  persona.getDireccion().getCp();
            stmt.setString(5, direccionSQL); 
            stmt.setInt(6, persona.getIdPersona());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarPersona(int idPersona) {
        String query = "DELETE FROM persona WHERE id_persona = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idPersona);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Persona mapRowToPersona(ResultSet rs) throws SQLException {
        int idPersona = rs.getInt("id_persona");
        String nombre = rs.getString("nombre");
        String apellidoPaterno = rs.getString("apellido_paterno");
        String apellidoMaterno = rs.getString("apellido_materno");
        String telefono = rs.getString("telefono");

        // Extraer y mapear la dirección 
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());

        return new Persona(idPersona, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion);
    }
}
