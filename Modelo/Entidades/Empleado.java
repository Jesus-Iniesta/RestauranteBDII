/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.math.BigDecimal;
import java.time.LocalDate;

public class Empleado extends Persona {
    private String rfc;
    private String curp;
    private BigDecimal salario;
    private LocalDate fechaContratacion;
    private int idRestaurante;
    private int idHorario;
    private int idPuesto;

    // Constructor
    public Empleado(int idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, 
                    String telefono, Direccion direccion, String rfc, String curp, BigDecimal salario, 
                    LocalDate fechaContratacion, int idRestaurante, int idHorario, int idPuesto) {
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, telefono, direccion);
        this.rfc = rfc;
        this.curp = curp;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.idRestaurante = idRestaurante;
        this.idHorario = idHorario;
        this.idPuesto = idPuesto;
    }

    // Getters y setters
    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }
}

