
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Horario {

    private int idHorario;
    private LocalDate dia;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;

    // Constructor
    public Horario(int idHorario, LocalDate dia, LocalDateTime horaInicio, LocalDateTime horaFin) {
        this.idHorario = idHorario;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    // Getters y setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalDateTime horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Horario{" +
               "idHorario=" + idHorario +
               ", dia=" + dia +
               ", horaInicio=" + horaInicio +
               ", horaFin=" + horaFin +
               '}';
    }
}

