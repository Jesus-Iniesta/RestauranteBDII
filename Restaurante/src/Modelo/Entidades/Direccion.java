
package Modelo.Entidades;

/**
 *
 * @author Enrique
 */
public class Direccion {
    private String calle;
    private String colonia;
    private String pais;
    private String cp;

    // Constructor
    public Direccion(String calle, String colonia, String pais, String cp) {
        this.calle = calle;
        this.colonia = colonia;
        this.pais = pais;
        this.cp = cp;
    }

    public Direccion() {
    }

    // Getters y setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    @Override
    public String toString() {
        return "Direccion{" +
               "calle='" + calle + '\'' +
               ", colonia='" + colonia + '\'' +
               ", pais='" + pais + '\'' +
               ", cp='" + cp + '\'' +
               '}';
    }
}
