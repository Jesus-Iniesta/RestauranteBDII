/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class Cliente extends Persona {
    private String correo;

    // Constructor
    public Cliente(int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, 
                   String telefono, Direccion direccion, String correo) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion);
        this.correo = correo;
    }

    // Getter y setter
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
