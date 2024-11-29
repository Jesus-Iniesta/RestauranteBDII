package Modelo.Control;

import Modelo.Entidades.Puesto;
import java.util.ArrayList;

/**
 *
 * @author Jesús
 */
public class ListaPuesto {
    ArrayList<Puesto> lista;

    public ListaPuesto() {
        lista = new ArrayList();
    }
    //metodos para agregar productos a un arreglo
    public void AgregarPuestos(Puesto pueestos){
        lista.add(pueestos);
    }
    
}
