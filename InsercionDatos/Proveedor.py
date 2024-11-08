from EmpleadoFaker import limpiar_pantalla
from faker import Faker
from conexion import connect_to_db

fake = Faker("es_MX")

def insert_proveedor_data(cursor):
    limpiar_pantalla()
    for i in range(20_000):
        nombre_proveedor = fake.company()
        telefono_proveedor = fake.phone_number()
        direccion = [f"{fake.street_name()} {fake.building_number()}", fake.city(), "México", fake.postcode()]
        
        query = """
        INSERT INTO prueba_bd.proveedor (nombre,telefono,direccion)
        VALUES (%s,%s,ROW(%s,%s,%s,%s))
        """
        cursor.execute(query, (nombre_proveedor,telefono_proveedor,
                               direccion[0], direccion[1], direccion[2], direccion[3]))
        print(f"Inserción de restaurante: {i+1}", end='\r')

def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        insert_proveedor_data(cursor)
        conn.commit()
        print("\nInserción terminada")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()