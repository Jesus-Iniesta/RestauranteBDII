/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 *
 * @author jesus
 */

/*
!!!!!!
ESTA PARTE DE CÓDIGO SOLO ES UNA PRUEBA A UNA BASE DE DATOS LOCAL
!!!!
*/
public class Conexion {
    private final String url = "jdbc:postgresql://localhost:5432/prueba_proyecto";
    private final String user = "postgres";
    private final String password = "1234";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a PostgreSQL");
            selectAllFromRestaurante();
        } catch (SQLException e) {
            System.out.println("Error en la conexión a PostgreSQL");
            e.printStackTrace();
        }
        return conn;
    }
     public void selectAllFromRestaurante() {
        String query = "SELECT * FROM restaurante";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Procesa los resultados de la consulta
            while (rs.next()) {
                int id = rs.getInt("id_restaurante"); // Cambia los nombres de las columnas según tu base de datos
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Dirección: " + direccion+ " ,Telefono: "+telefono);
            }
        } catch (SQLException e) {
            System.out.println("Error ejecutando el query");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Conexion conn = new Conexion();
        conn.connect();
    }
}
