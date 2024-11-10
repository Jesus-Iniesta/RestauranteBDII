from faker import Faker
from conexion import connect_to_db


fake = Faker('es_MX')  # Establece la localización en español para Faker


def insert_restaurante_data(cursor):
    for i in range(5000):  # Ajusta la cantidad de registros que deseas generar
        nombre_restaurante = f"Restaurante {fake.company()}"
        telefono = fake.phone_number()
        direccion = [f"{fake.street_name()} {fake.building_number()}", fake.city(), "Mexico", fake.postcode()]

        query = """
        INSERT INTO restaurante.restaurante (nombre, telefono, direccion)
        VALUES (%s, %s, ROW(%s, %s, %s, %s));
        """
        
        cursor.execute(query, (nombre_restaurante, telefono, 
                               direccion[0], direccion[1], direccion[2], direccion[3]))
        print(f"Inserción de restaurante: {i+1}", end='\r')
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        insert_restaurante_data(cursor)
        conn.commit()
        print("\nInserción restaurante terminada")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
