
package Modelo.Control;

import Modelo.Entidades.Productos;
import java.util.ArrayList;
/**
 *
 * @author jesus
 */
public class ListadoProductos {
    ArrayList<Productos>lista;
    
    //Constructor
    public ListadoProductos() {
        lista = new ArrayList();
    }
    //metodos para agregar productos a un arreglo
    public void AgregarProductos(Productos products){
        lista.add(products);
    }
}
