import csv
import psycopg2
import random
import time

#conexion a la base de datos
def connect_to_db():
    return psycopg2.connect(
        host="localhost",
        dbname = "prueba_proyecto",
        user = "postgres",
        password="1234"
    )
    
# Cargar datos de un archivo CSV en una lista
def load_csv_data(filename):
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        return [row[0] for row in reader]

#insertar datos en la tabla persona
def insert_persona_data(cursor, nombres,apellidos_paterno,apellidos_materno,telefonos,direcciones):
    for i in range(len(nombres)):
        nombre = nombres[i] # tomar un nombre de un arreglo de nombres
        apellido_paterno = random.choice(apellidos_paterno) #tomar un apellido de un arreglo de apellidos
        apellido_materno = random.choice(apellidos_materno) #tomar un apellido de un arreglo de apellidos
        telefono = random.choice(telefonos) #escojer un telfono al azar
        direccion = direcciones[i].split(',') #escojer una direccion del areglo direcciones
        #ejecutar el query de inserción de datos
        query = """
        INSERT INTO persona (nombre, apellido_paterno, apellido_materno, telefono, direccion)
        VALUES (%s, %s, %s, %s, ROW(%s, %s, %s, %s)::Direccion);
        """
        #Función execute para ejecutar el query en la base de datos
        cursor.execute(query, (nombre, apellido_paterno, apellido_materno, telefono, direccion[0], direccion[1], direccion[2], direccion[3]))
        print(f"Inserción: {i+1}/{range(len(nombres))}",end='\r')
        time.sleep(0.1)
    print("\n inserción de datos terminada")
    
# Programa principal
def main():
    # Cargar los datos de cada archivo CSV
    nombres = load_csv_data('/data/nombres.csv')
    apellidos_paternos = load_csv_data('/data/apellido_paterno.csv')
    apellidos_maternos = load_csv_data('/data/apellido_materno.csv')
    telefonos = load_csv_data('/data/telefono.csv')
    direcciones = load_csv_data('/data/direccion.csv')
    
    # Conectar a la base de datos y realizar la inserción
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_persona_data(cursor,nombres,apellidos_paternos,apellidos_maternos,telefonos,direcciones)
        conn.commit()
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    main()    