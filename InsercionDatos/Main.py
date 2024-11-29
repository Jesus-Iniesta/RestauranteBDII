#Insertar primero
#1.Restaurantes LISTO OK
#2.categorias LISTO OK
#3. proveedores LISTO OK
#4. productos Listo OK
#5. Almacen LISTO OK
#6. Horarios Listo OK
#7. Puestos LISTO  OK     
#8. empleados LISTO OK
#9. Cliente-venta-pedido
#10. FACTURAS
from Restaurante import main as insert_restaurante
from Categorias import main as insert_Category
from Proveedor import main as insert_proveedor
from Productos import main as insert_product
from Horarios import main as insert_horario
from Puestos import main as insert_puesto
from EmpleadoFaker import main as insert_empleado
from EmpleadoFaker import limpiar_pantalla
from Ventas import main as insert_venta
from Factura import main as insert_factura
import time

def main():
    print("""
██████╗ ██╗███████╗███╗   ██╗██╗   ██╗███████╗███╗   ██╗██╗██████╗  ██████╗      █████╗ ██╗         ██████╗ ██████╗  ██████╗  ██████╗ ██████╗  █████╗ ███╗   ███╗ █████╗     ██████╗ ███████╗    
██╔══██╗██║██╔════╝████╗  ██║██║   ██║██╔════╝████╗  ██║██║██╔══██╗██╔═══██╗    ██╔══██╗██║         ██╔══██╗██╔══██╗██╔═══██╗██╔════╝ ██╔══██╗██╔══██╗████╗ ████║██╔══██╗    ██╔══██╗██╔════╝    
██████╔╝██║█████╗  ██╔██╗ ██║██║   ██║█████╗  ██╔██╗ ██║██║██║  ██║██║   ██║    ███████║██║         ██████╔╝██████╔╝██║   ██║██║  ███╗██████╔╝███████║██╔████╔██║███████║    ██║  ██║█████╗      
██╔══██╗██║██╔══╝  ██║╚██╗██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║██║██║  ██║██║   ██║    ██╔══██║██║         ██╔═══╝ ██╔══██╗██║   ██║██║   ██║██╔══██╗██╔══██║██║╚██╔╝██║██╔══██║    ██║  ██║██╔══╝      
██████╔╝██║███████╗██║ ╚████║ ╚████╔╝ ███████╗██║ ╚████║██║██████╔╝╚██████╔╝    ██║  ██║███████╗    ██║     ██║  ██║╚██████╔╝╚██████╔╝██║  ██║██║  ██║██║ ╚═╝ ██║██║  ██║    ██████╔╝███████╗    
╚═════╝ ╚═╝╚══════╝╚═╝  ╚═══╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝╚═╝╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚══════╝    ╚═╝     ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝    ╚═════╝ ╚══════╝    
                                                                                                                                                                                                 
██╗███╗   ██╗███████╗███████╗██████╗  ██████╗██╗ ██████╗ ███╗   ██╗    ██████╗ ███████╗    ██████╗  █████╗ ████████╗ ██████╗ ███████╗                                                            
██║████╗  ██║██╔════╝██╔════╝██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║    ██╔══██╗██╔════╝    ██╔══██╗██╔══██╗╚══██╔══╝██╔═══██╗██╔════╝                                                            
██║██╔██╗ ██║███████╗█████╗  ██████╔╝██║     ██║██║   ██║██╔██╗ ██║    ██║  ██║█████╗      ██║  ██║███████║   ██║   ██║   ██║███████╗                                                            
██║██║╚██╗██║╚════██║██╔══╝  ██╔══██╗██║     ██║██║   ██║██║╚██╗██║    ██║  ██║██╔══╝      ██║  ██║██╔══██║   ██║   ██║   ██║╚════██║                                                            
██║██║ ╚████║███████║███████╗██║  ██║╚██████╗██║╚██████╔╝██║ ╚████║    ██████╔╝███████╗    ██████╔╝██║  ██║   ██║   ╚██████╔╝███████║                                                            
╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝    ╚═════╝ ╚══════╝    ╚═════╝ ╚═╝  ╚═╝   ╚═╝    ╚═════╝ ╚══════╝    
          """)
    
    time.sleep(2)
    limpiar_pantalla()
    #insert_restaurante()
    #insert_Category()
    #insert_proveedor()
    #insert_product()
    #insert_horario()
    #insert_puesto()
    #insert_empleado()
    #insert_venta()
    insert_factura()
    print("""
██ ███    ██ ███████ ███████ ██████   ██████ ██  ██████  ███    ██      ██████  ██████  ███    ██  ██████ ██      ██    ██ ██ ██████   █████  
██ ████   ██ ██      ██      ██   ██ ██      ██ ██    ██ ████   ██     ██      ██    ██ ████   ██ ██      ██      ██    ██ ██ ██   ██ ██   ██ 
██ ██ ██  ██ ███████ █████   ██████  ██      ██ ██    ██ ██ ██  ██     ██      ██    ██ ██ ██  ██ ██      ██      ██    ██ ██ ██   ██ ███████ 
██ ██  ██ ██      ██ ██      ██   ██ ██      ██ ██    ██ ██  ██ ██     ██      ██    ██ ██  ██ ██ ██      ██      ██    ██ ██ ██   ██ ██   ██ 
██ ██   ████ ███████ ███████ ██   ██  ██████ ██  ██████  ██   ████      ██████  ██████  ██   ████  ██████ ███████  ██████  ██ ██████  ██   ██
          """)
if __name__ == '__main__':
    main()