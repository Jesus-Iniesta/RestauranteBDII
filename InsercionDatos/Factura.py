import random
from conexion import connect_to_db
from faker import Faker
from GenerarRFC import generar_rfc
from GenerarFechaNac import generar_fecha_aleatoria

def insert_data_factura(cursor):
    for i in range(1,10_000):
        id_cliente = i
        query_nombre = "SELECT nombre FROM restaurante.cliente where id_cliente = %s"
        cursor.execute(query_nombre,(id_cliente,))
        nombre = cursor.fetchone()[0]
        
        query_apellidoPaterno = "SELECT apellido_paterno FROM restaurante.cliente where id_cliente = %s"
        cursor.execute(query_apellidoPaterno,(id_cliente,))
        apellido_paterno = cursor.fetchone()[0]
        
        query_apellidoMaterno = "SELECT apellido_materno FROM restaurante.cliente where id_cliente = %s"
        cursor.execute(query_apellidoMaterno,(id_cliente,))
        apellido_materno = cursor.fetchone()[0]
        
        fecha_nac = generar_fecha_aleatoria()
        rfc_cliente = generar_rfc(nombre,apellido_paterno,apellido_materno,fecha_nac)
        #id_venta
        query_venta = "SELECT id_venta from restaurante.venta where id_cliente = %s"
        cursor.execute(query_venta, (id_cliente,))
        id_venta = cursor.fetchone()[0]
        
        query_id_pedido = "SELECT id_pedido from restaurante.pedidos where id_venta = %s"
        cursor.execute(query_id_pedido,(id_venta,))
        id_pedido = cursor.fetchone()[0]

        id_restaurante = random.randint(1,206)
        query_restaurante = "SELECT nombre from restaurante.restaurante where id_restaurante = %s"
        cursor.execute(query_restaurante,(id_restaurante,))
        emisor = cursor.fetchone()[0]
        
        query_factura = """
        INSERT INTO restaurante.factura(rfc_cliente,nombre_cliente,fecha_expedicion,emisor,id_cliente,id_pedido)
        VALUES (%s,%s,current_date,%s,%s,%s)
        """
        cursor.execute(query_factura,(rfc_cliente,nombre,emisor,id_cliente,id_pedido))
        print(f"Inserción Facutura: {(i+1):,}", end='\r')

# Programa principal
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        insert_data_factura(cursor)
        conn.commit()
        print("\nInserción FACTURAS completada.")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
