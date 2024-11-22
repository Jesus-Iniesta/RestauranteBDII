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
import java.time.LocalDate;
import java.time.LocalDateTime;
import Modelo.Entidades.*;
import Util.Conexion;

public class HorarioDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public HorarioDAO(Connection conn) {
        this.conn = conn;
    }


    public Horario crearHorario(Horario horario) {
        String query = "INSERT INTO horario (dia, hora_inicio, hora_fin) " +
                       "VALUES (?, ?, ?) RETURNING id_horario";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(horario.getDia()));  // Convertir LocalDate a Date
            stmt.setTimestamp(2, Timestamp.valueOf(horario.getHoraInicio()));  // Convertir LocalDateTime a Timestamp
            stmt.setTimestamp(3, Timestamp.valueOf(horario.getHoraFin()));  // Convertir LocalDateTime a Timestamp

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                horario.setIdHorario(rs.getInt("id_horario"));
            }
            return horario;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Horario obtenerHorarioPorId(int idHorario) {
        String query = "SELECT id_horario, dia, hora_inicio, hora_fin FROM horario WHERE id_horario = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idHorario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToHorario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Horario> obtenerTodosLosHorarios() {
        String query = "SELECT id_horario, dia, hora_inicio, hora_fin FROM horario";
        List<Horario> horarios = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                horarios.add(mapRowToHorario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

    public boolean actualizarHorario(Horario horario) {
        String query = "UPDATE horario SET dia = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(horario.getDia()));
            stmt.setTimestamp(2, Timestamp.valueOf(horario.getHoraInicio()));
            stmt.setTimestamp(3, Timestamp.valueOf(horario.getHoraFin()));
            stmt.setInt(4, horario.getIdHorario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarHorario(int idHorario) {
        String query = "DELETE FROM horario WHERE id_horario = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idHorario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Horario mapRowToHorario(ResultSet rs) throws SQLException {
        int idHorario = rs.getInt("id_horario");
        LocalDate dia = rs.getDate("dia").toLocalDate();  // Convertir Date a LocalDate
        LocalDateTime horaInicio = rs.getTimestamp("hora_inicio").toLocalDateTime();  // Convertir Timestamp a LocalDateTime
        LocalDateTime horaFin = rs.getTimestamp("hora_fin").toLocalDateTime();  // Convertir Timestamp a LocalDateTime

        return new Horario(idHorario, dia, horaInicio, horaFin);
    }

}
