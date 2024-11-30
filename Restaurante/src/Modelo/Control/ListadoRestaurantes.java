
package Modelo.Control;


import Modelo.Entidades.Restaurante;
import java.util.ArrayList;

/**
 *
 * @author pigim
 */
public class ListadoRestaurantes {
     ArrayList<Restaurante>lista;
    
    //Constructor
    public ListadoRestaurantes() {
        lista = new ArrayList();
    }
    //metodos para agregar productos a un arreglo
    public void Agregaremisores(Restaurante restaurante){
        lista.add(restaurante);
    }
}
