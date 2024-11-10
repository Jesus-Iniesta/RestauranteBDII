import random
from conexion import connect_to_db
from faker import Faker

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
    INSERT INTO cliente (nombre, apellido_paterno, apellido_materno, telefono, direccion, correo)
    VALUES (%s, %s, %s, %s, ROW(%s, %s, %s, %s), %s)
    RETURNING id_cliente;
    """
    
    cursor.execute(query_cliente, (nombre, apellido_paterno, apellido_materno, telefono,
                                   direccion[0], direccion[1], direccion[2], direccion[3], correo))
    id_cliente = cursor.fetchone()[0]  # Obtener el ID del cliente insertado
    return id_cliente

# Función para insertar una venta y un pedido "A domicilio"
def insert_ventas_domicilio(cursor):
    id_cliente = insert_cliente(cursor)
    IVA = round(random.uniform(0.05, 0.20), 2)
    cliente = f"{fake.first_name()} {fake.last_name()}"
    fecha_venta = fake.date_this_decade()
    descuento = round(random.uniform(0, 0.30), 2)

    # Insertar la venta
    query_venta = """
    INSERT INTO venta (IVA, cliente, fecha_venta, descuento, id_cliente)
    VALUES (%s, %s, %s, %s, %s)
    RETURNING id_venta;
    """
    
    cursor.execute(query_venta, (IVA, cliente, fecha_venta, descuento, id_cliente))
    id_venta = cursor.fetchone()[0]  # Obtener el ID de la venta insertada

    # Insertar un pedido tipo "A domicilio"
    precio = round(random.uniform(10, 100), 2)
    cantidad = random.randint(1, 5)
    tipo = "A domicilio"
    producto = fake.word()
    metodo_pago = random.choice(["Tarjeta", "Efectivo", "Transferencia"])

    query_pedido = """
    INSERT INTO pedidos (precio, cantidad, tipo, productos, metodo_pago, id_venta, fecha_venta)
    VALUES (%s, %s, %s, %s, %s, %s, CURRENT_DATE);
    """
    
    cursor.execute(query_pedido, (precio, cantidad, tipo, producto, metodo_pago, id_venta))
    print(f"Inserción VENTA A DOMICILIO: Cliente ID {id_cliente}, Venta ID {id_venta}")

# Función para insertar una venta y un pedido "Restaurante"
def insert_ventas_restaurante(cursor):
    id_cliente = random.randint(1, 100)  # Suponiendo que ya existen clientes en esta tabla
    IVA = round(random.uniform(0.05, 0.20), 2)
    cliente = f"{fake.first_name()} {fake.last_name()}"
    fecha_venta = fake.date_this_decade()
    descuento = round(random.uniform(0, 0.30), 2)

    # Insertar la venta
    query_venta = """
    INSERT INTO venta (IVA, cliente, fecha_venta, descuento, id_cliente)
    VALUES (%s, %s, %s, %s, %s)
    RETURNING id_venta;
    """
    
    cursor.execute(query_venta, (IVA, cliente, fecha_venta, descuento, id_cliente))
    id_venta = cursor.fetchone()[0]  # Obtener el ID de la venta insertada

    # Insertar un pedido tipo "Restaurante"
    precio = round(random.uniform(10, 100), 2)
    cantidad = random.randint(1, 5)
    tipo = "Restaurante"
    producto = fake.word()
    metodo_pago = random.choice(["Tarjeta", "Efectivo", "Transferencia"])

    query_pedido = """
    INSERT INTO pedidos (precio, cantidad, tipo, productos, metodo_pago, id_venta, fecha_venta)
    VALUES (%s, %s, %s, %s, %s, %s, CURRENT_DATE);
    """
    
    cursor.execute(query_pedido, (precio, cantidad, tipo, producto, metodo_pago, id_venta))
    print(f"Inserción VENTA RESTAURANTE: Cliente ID {id_cliente}, Venta ID {id_venta}")

# Programa principal
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        # Realizar inserciones para ventas a domicilio y en restaurante
        insert_ventas_domicilio(cursor)  # Inserta una venta "A domicilio"
        insert_ventas_restaurante(cursor)  # Inserta una venta "Restaurante"
        
        conn.commit()
        print("\nInserción completada.")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
