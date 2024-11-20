/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
         String url = "jdbc:postgresql://100.88.248.149:5432/Restaurante";
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
}
