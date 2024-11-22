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
import java.math.BigDecimal;
import java.time.LocalDate;
import Modelo.Entidades.*;
import Util.Conexion;

public class EmpleadoDAO {


    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public EmpleadoDAO(Connection conn) {
        this.conn = conn;
    }

    public Empleado crearEmpleado(Empleado empleado) {
        String query = "INSERT INTO empleado (RFC, CURP, salario, fecha_contratacion, id_restaurante, id_horario, id_puesto) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_empleado";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, empleado.getRfc());
            stmt.setString(2, empleado.getCurp());
            stmt.setBigDecimal(3, empleado.getSalario());
            stmt.setDate(4, Date.valueOf(empleado.getFechaContratacion()));
            stmt.setInt(5, empleado.getIdRestaurante());
            stmt.setInt(6, empleado.getIdHorario());
            stmt.setInt(7, empleado.getIdPuesto());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empleado.setIdPersona(rs.getInt("id_empleado"));
            }
            return empleado;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Empleado obtenerEmpleadoPorId(int idEmpleado) {
        String query = "SELECT id_empleado, RFC, CURP, salario, fecha_contratacion, id_restaurante, id_horario, id_puesto " +
                       "FROM empleado WHERE id_empleado = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRowToEmpleado(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        String query = "SELECT id_empleado, RFC, CURP, salario, fecha_contratacion, id_restaurante, id_horario, id_puesto " +
                       "FROM empleado";
        List<Empleado> empleados = new ArrayList<>();

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapRowToEmpleado(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public boolean actualizarEmpleado(Empleado empleado) {
        String query = "UPDATE empleado SET RFC = ?, CURP = ?, salario = ?, fecha_contratacion = ?, " +
                       "id_restaurante = ?, id_horario = ?, id_puesto = ? WHERE id_empleado = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setString(1, empleado.getRfc());
            stmt.setString(2, empleado.getCurp());
            stmt.setBigDecimal(3, empleado.getSalario());
            stmt.setDate(4, Date.valueOf(empleado.getFechaContratacion()));
            stmt.setInt(5, empleado.getIdRestaurante());
            stmt.setInt(6, empleado.getIdHorario());
            stmt.setInt(7, empleado.getIdPuesto());
            stmt.setInt(8, empleado.getIdPersona());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarEmpleado(int idEmpleado) {
        String query = "DELETE FROM empleado WHERE id_empleado = ?";

        try (
             PreparedStatement stmt = this.conn.prepareStatement(query)) {

            stmt.setInt(1, idEmpleado);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Empleado mapRowToEmpleado(ResultSet rs) throws SQLException {
        int idEmpleado = rs.getInt("id_empleado");
        
        
        String nombre = rs.getString("nombre");
        String apellidoPaterno = rs.getString("apellidoPaterno");
        String apellidoMaterno = rs.getString("apellidoMaterno");
        String telefono = rs.getString("telefono");
        
        
        String direccionString = rs.getString("direccion");
        String[] direccionParts = direccionString.split(",");

        Direccion direccion = new Direccion(direccionParts[0].trim(), direccionParts[1].trim(),
                                             direccionParts[2].trim(), direccionParts[3].trim());
        
        String rfc = rs.getString("RFC");
        String curp = rs.getString("CURP");
        BigDecimal salario = rs.getBigDecimal("salario");
        LocalDate fechaContratacion = rs.getDate("fecha_contratacion").toLocalDate();
        int idRestaurante = rs.getInt("id_restaurante");
        int idHorario = rs.getInt("id_horario");
        int idPuesto = rs.getInt("id_puesto");

        return new Empleado(idEmpleado, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion  , rfc, curp, salario, fechaContratacion, idRestaurante, idHorario, idPuesto);
        
        
    }
}

