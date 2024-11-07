import psycopg2
import random
import os
from faker import Faker
from GenerarRFC import generar_rfc
from GenerarFechaNac import generar_fecha_aleatoria
from GenerarCurp import generar_curp
from faker.providers import BaseProvider

class MexicanIDProvider(BaseProvider):
    def rfc(self, nombre, apellido_paterno, apellido_materno, fecha_nacimiento):
        return generar_rfc(nombre, apellido_paterno, apellido_materno, fecha_nacimiento)
    
    def curp(self, nombre, apellido_paterno, apellido_materno, fecha_nacimiento, estado):
        return generar_curp(nombre, apellido_paterno, apellido_materno, fecha_nacimiento, estado)

fake = Faker('es_MX')  # Establece la localización en español para Faker
fake.add_provider(MexicanIDProvider)

# Conexión a la base de datos
def connect_to_db():
    return psycopg2.connect(
        host="localhost",
        dbname="prueba_proyecto",
        user="postgres",
        password="1234"
    )

def limpiar_pantalla():
    if os.name == 'nt':
        os.system('cls')
    else:
        os.system('clear')

# Generar datos de persona con Faker y función para insertar en la base de datos
def insert_persona_empleado_data(cursor):
    opcion = True
    while(opcion):
        limpiar_pantalla()
        print("Escribe el limite de inseciones a realizar como limite 1,000,000 donde: \n1=10,000 y 100 = 1,000,000")
        cantidad = int(input())*10000
        if cantidad <= 1_000_000:
            for i in range(cantidad):
                nombre = fake.first_name()
                apellido_paterno = fake.last_name()
                apellido_materno = fake.last_name()
                telefono = fake.phone_number()
                direccion = [f"{fake.street_name()} {fake.building_number()}", fake.state(), "México", fake.postcode()]

                fecha_nac_aleatoria = generar_fecha_aleatoria()
                rfc = fake.rfc(nombre, apellido_paterno, apellido_materno, fecha_nac_aleatoria)
                curp = fake.curp(nombre, apellido_paterno, apellido_materno, fecha_nac_aleatoria, direccion[2])
                    
                id_puesto = 1
                salario =  3500
                id_restaurante = 1
                id_horario = 1
                    
                query = """
                INSERT INTO prueba_bd.empleado (nombre, apellido_paterno, apellido_materno, telefono, direccion, rfc, curp, salario, fecha_contratacion, id_restaurante, id_horario, id_puesto)
                VALUES (%s, %s, %s, %s, ROW(%s, %s, %s, %s), %s, %s, %s, CURRENT_DATE, %s, %s, %s);
                """
                    
                cursor.execute(query, (nombre, apellido_paterno, apellido_materno, telefono, 
                                        direccion[0], direccion[1], direccion[2], direccion[3],
                                        rfc, curp, salario, id_restaurante, id_horario, id_puesto))
                print(f"Inserción: {(i+1):,}", end='\r')
        control = input("Deseas continuar? 's' para seguir 'n' para deterner y hacer commit")
        if control.lower() == 's':
            continue
        if control.lower() == 'n':
            opcion = False

# Programa principal
def main():
    conn = connect_to_db()
    cursor = conn.cursor()
    
    try:
        insert_persona_empleado_data(cursor)
        conn.commit()
        print("Inserción terminada")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()
