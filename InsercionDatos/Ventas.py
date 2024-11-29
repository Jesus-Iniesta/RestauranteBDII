import random
from conexion import connect_to_db
from faker import Faker
from datetime import date

fake = Faker('es_MX')  # Localización en español para Faker

# Función para insertar cliente en la base de datos
def insert_cliente(cursor):
    nombre = fake.first_name()
    apellido_paterno = fake.last_name()
    apellido_materno = fake.last_name()
    telefono = fake.phone_number()
    correo = fake.email()
    direccion = [fake.street_name(), fake.secondary_address(), "Mexico", fake.postcode()]

    query_cliente = """
    INSERT INTO restaurante.cliente (nombre, apellido_paterno, apellido_materno, telefono, direccion, correo)
    VALUES (%s, %s, %s, %s, ROW(%s, %s, %s, %s), %s)
    RETURNING id_cliente;
    """
    
    cursor.execute(query_cliente, (nombre, apellido_paterno, apellido_materno, telefono,
                                   direccion[0], direccion[1], direccion[2], direccion[3], correo))
    id_cliente = cursor.fetchone()[0]  # Obtener el ID del cliente insertado
    return id_cliente

def insert_ventas_domicilio(cursor):
    for i in range(750_000):
        # Insertar el cliente y obtener su ID
        id_cliente = insert_cliente(cursor)  
        IVA = round(random.uniform(0.05, 0.20), 2)
        fecha_venta = fake.date_between_dates(date_start=date(2024, 1, 1), date_end=date(2024, 6, 28))
        descuento = round(random.uniform(0, 0.30), 2)

        # Insertar la venta y obtener el ID de la venta
        query_venta = """
        INSERT INTO restaurante.venta (IVA, fecha_venta, descuento, id_cliente)
        VALUES (%s, %s, %s, %s)
        RETURNING id_venta;
        """
        cursor.execute(query_venta, (IVA, fecha_venta, descuento, id_cliente))
        id_venta = cursor.fetchone()[0]  # Obtener el ID de la venta insertada

        # Preparar datos para el pedido tipo "A domicilio"
        cantidades = [random.randint(1, 5) for _ in range(3)]  # Ejemplo de cantidades para 3 productos
        id_productos = [random.randint(1,150) for _ in range(3)]  # IDs de productos de ejemplo
        tipo = "A domicilio"
        metodo_pago = random.choice(["Tarjeta", "Efectivo", "Transferencia"])

        # Insertar un pedido asociado a la venta
        query_pedido = """
        INSERT INTO restaurante.pedidos (id_venta, cantidad, tipo, metodo_pago, id_producto, fecha_venta)
        VALUES (%s, %s, %s, %s, %s, %s);
        """
        
        cursor.execute(query_pedido, (id_venta, cantidades, tipo, metodo_pago, id_productos, fecha_venta))
        print(f"Inserción VENTA DOMICILIO: {(i+1):,}", end='\r')


# Función para insertar una venta y un pedido "Restaurante"
def insert_ventas_restaurante(cursor):
    for i in range(1_500_000):
        query_cliente = """
        INSERT INTO restaurante.cliente_temp (id_cliente_temp) VALUES (DEFAULT) RETURNING id_cliente_temp;
        """
        cursor.execute(query_cliente)
        # Insertar el cliente y obtener su ID
        id_cliente = cursor.fetchone()[0]
        IVA = round(random.uniform(0.05, 0.20), 2)
        fecha_venta = fake.date_between_dates(date_start=date(2024, 7, 3), date_end=date(2024, 12, 31))
        descuento = round(random.uniform(0, 0.30), 2)

        # Insertar la venta y obtener el ID de la venta
        query_venta = """
        INSERT INTO restaurante.venta (IVA, fecha_venta, descuento, no_cliente_temp)
        VALUES (%s, %s, %s, %s)
        RETURNING id_venta;
        """
        cursor.execute(query_venta, (IVA, fecha_venta, descuento, id_cliente))
        id_venta = cursor.fetchone()[0]  # Obtener el ID de la venta insertada

        # Preparar datos para el pedido tipo "A domicilio"
        cantidades = [random.randint(1, 5) for _ in range(3)]  # Ejemplo de cantidades para 3 productos
        id_productos = [random.randint(1,150) for _ in range(3)]  # IDs de productos de ejemplo
        tipo = "En restaurante"
        metodo_pago = random.choice(["Tarjeta", "Efectivo", "Transferencia"])

        # Insertar un pedido asociado a la venta
        query_pedido = """
        INSERT INTO restaurante.pedidos (id_venta, cantidad, tipo, metodo_pago, id_producto, fecha_venta)
        VALUES (%s, %s, %s, %s, %s, %s);
        """
        
        cursor.execute(query_pedido, (id_venta, cantidades, tipo, metodo_pago, id_productos, fecha_venta))
        print(f"Inserción VENTA RESTAURANTE: {(i+1):,}", end='\r')

# Programa principal
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        # Realizar inserciones para ventas a domicilio y en restaurante
        insert_ventas_domicilio(cursor)  # Inserta una venta "A domicilio"
        print("\n")
        insert_ventas_restaurante(cursor)  # Inserta una venta "Restaurante"
        
        conn.commit()
        print("\nInserción VENTAS completada.")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
