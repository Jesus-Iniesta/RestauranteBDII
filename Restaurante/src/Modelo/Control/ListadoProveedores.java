
package Modelo.Control;

import Modelo.Entidades.Proveedor;
import java.util.ArrayList;
/**
 *
 * @author jesus
 */
public class ListadoProveedores {
    ArrayList<Proveedor> lista;
    
    //constructor

    public ListadoProveedores() {
        lista = new ArrayList();
    }
    //metodos para agregar proveedores a un arreglo
    public void AgregarProveedores(Proveedor prov){
        lista.add(prov);
    }
}
