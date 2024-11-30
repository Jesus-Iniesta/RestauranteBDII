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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Modelo.Entidades.*;
import Util.Conexion;
import javax.swing.table.DefaultTableModel;

public class HistorialEmpleadoDAO {

    private Connection conn;

    //Constructora que obtiene la conexion a la base de datos
    public HistorialEmpleadoDAO(Connection conn) {
        this.conn = conn;
    }

    // Insertar un nuevo historial de empleado
    public boolean insertarHistorialEmpleado(HistorialEmpleado historialEmpleado) {
        String sql = "INSERT INTO historial_empleado (fecha_inicioPuesto, fecha_finPuesto, id_puesto) VALUES (?, ?, ?) RETURNING id_empleado";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(historialEmpleado.getFechaInicioPuesto()));
            stmt.setDate(2, Date.valueOf(historialEmpleado.getFechaFinPuesto()));
            stmt.setInt(3, historialEmpleado.getIdPuesto());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                historialEmpleado.setIdEmpleado(rs.getInt("id_empleado"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener historial de empleado por ID
    public HistorialEmpleado obtenerHistorialPorIdEmpleado(int idEmpleado) {
        String sql = "SELECT * FROM historial_empleado WHERE id_empleado = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new HistorialEmpleado(
                        rs.getInt("id_empleado"),
                        rs.getDate("fecha_inicioPuesto").toLocalDate(),
                        rs.getDate("fecha_finPuesto").toLocalDate(),
                        rs.getInt("id_puesto")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Obtener todos los historiales de empleados
    public void obtenerTodosLosHistoriales(DefaultTableModel modeloTabla) {
        
        String query = "SELECT \n" +
            "    he.id_empleado AS id_empleado,\n" +
            "    p.titulo_puesto AS puesto,\n" +
            "    he.id_rfc AS rfc,\n" +
            "    he.fecha_iniciopuesto AS fecha_inicio,\n" +
            "    he.fecha_finpuesto AS fecha_fin\n" +
            "FROM \n" +
            "    restaurante.historial_empleado he\n" +
            "JOIN \n" +
            "    restaurante.puesto p \n" +
            "    ON he.id_puesto = p.id_puesto\n" +
            "ORDER BY \n" +
            "    he.id_empleado";
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

    // Eliminar historial de empleado por ID
    public boolean eliminarHistorialEmpleado(int idEmpleado) {
        String sql = "DELETE FROM historial_empleado WHERE id_empleado = ?";
        try (
             PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, idEmpleado);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

