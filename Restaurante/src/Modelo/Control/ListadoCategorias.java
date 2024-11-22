
package Modelo.Control;

import Modelo.Entidades.Categoria;
import java.util.ArrayList;
/**
 *
 * @author Jesus
 */
public class ListadoCategorias {
    ArrayList<Categoria>lista;
    
    //Constructor
    public ListadoCategorias() {
        lista = new ArrayList();
    }
    //metodos para agregar productos a un arreglo
    public void AgregarCategorias(Categoria categorias){
        lista.add(categorias);
    }
}
