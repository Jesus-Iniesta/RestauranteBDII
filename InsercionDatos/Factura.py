import random
from conexion import connect_to_db
from faker import Faker
from GenerarRFC import generar_rfc
from GenerarFechaNac import generar_fecha_aleatoria

def cargar_restaurantes(cursor):
    """Carga todos los nombres de restaurantes en un diccionario."""
    query = "SELECT id_restaurante, nombre FROM restaurante.restaurante"
    cursor.execute(query)
    return {row[0]: row[1] for row in cursor.fetchall()}

def obtener_clientes_validos(cursor, limite):
    """Obtiene clientes que tienen ventas y pedidos asociados para generar facturas."""
    query = f"""
    SELECT DISTINCT c.id_cliente, c.nombre, c.apellido_paterno, c.apellido_materno, 
           v.id_venta, p.id_pedido
    FROM restaurante.cliente c
    INNER JOIN restaurante.venta v ON c.id_cliente = v.id_cliente
    INNER JOIN restaurante.pedidos p ON v.id_venta = p.id_venta
    LIMIT {limite}
    """
    cursor.execute(query)
    return cursor.fetchall()

def insert_data_factura(cursor, total_facturas=5000):
    # Cargar datos necesarios en memoria
    restaurantes = cargar_restaurantes(cursor)
    clientes = obtener_clientes_validos(cursor, total_facturas)

    if len(clientes) < total_facturas:
        print(f"Advertencia: Solo se encontraron {len(clientes)} clientes válidos para facturación.")
    
    # Generar facturas
    for i, cliente in enumerate(clientes, start=1):
        id_cliente, nombre, apellido_paterno, apellido_materno, id_venta, id_pedido = cliente

        fecha_nac = generar_fecha_aleatoria()
        rfc_cliente = generar_rfc(nombre, apellido_paterno, apellido_materno, fecha_nac)
        
        # Seleccionar un restaurante aleatorio
        id_restaurante = random.choice(list(restaurantes.keys()))
        emisor = restaurantes[id_restaurante]

        # Insertar factura
        query_factura = """
        INSERT INTO restaurante.factura(rfc_cliente, nombre_cliente, fecha_expedicion, emisor, id_cliente, id_pedido)
        VALUES (%s, %s, current_date, %s, %s, %s)
        """
        cursor.execute(query_factura, (rfc_cliente, nombre, emisor, id_cliente, id_pedido))

        # Progreso
        print(f"Inserción Factura {i:,}/{total_facturas:,}", end='\r')

# Programa principal
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        insert_data_factura(cursor, total_facturas=750_000)
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
