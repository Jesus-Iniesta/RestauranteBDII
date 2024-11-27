
package Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author jesus
 */


public class Conexion {
    private Connection connect;
    
    public Connection conectar(String usuario,String contraseña) {
         String url = "jdbc:postgresql://localhost:5432/prueba_proyecto";
         try{
             connect = DriverManager.getConnection(url, usuario, contraseña);
             System.out.println("Conexión exitosa");
         } catch(SQLException e ){
             System.out.println("Error al conectar: "+e.getMessage());
             connect = null;
         }
         return connect;
    }
    
    public void cerrarConexion(){
        if(connect != null){
            try{
                connect.close();
                System.out.println("Conexion cerrada");
            } catch(SQLException e){
                System.out.println("Error al cerrar la conexión: "+e.getMessage());
            }
        }
    }
    
    public Connection obtenerConexionActiva() {
    try {
        if (connect == null || connect.isClosed()) {
            throw new SQLException("La conexión no está activa.");
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener conexión activa: " + e.getMessage());
        return null;
    }
    return connect;
}
}
